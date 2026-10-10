package com.github.mojewski.footballleaguesimulator.service.team;

import com.github.mojewski.footballleaguesimulator.TODO.Calendar;
import com.github.mojewski.footballleaguesimulator.TODO.Simulation;
import com.github.mojewski.footballleaguesimulator.model.player.*;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.player.SalaryCalculator;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @Mock
    private RandomNumberGenerator random;
    @Mock
    private SalaryCalculator salaryCalculator;
    @Mock
    private Simulation simulation;
    @Mock
    private FreeAgents freeAgents;

    @InjectMocks
    private TransferService transferService;

    private Team buyingTeam;
    private Team sellingTeam;
    private Player player;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        buyingTeam = mock(Team.class);
        sellingTeam = mock(Team.class);
        player = mock(Player.class);
        calendar = new Calendar();

        lenient().when(buyingTeam.getReputation()).thenReturn(70);
        lenient().when(buyingTeam.getBudget()).thenReturn(100_000.0);
        lenient().when(player.getPosition()).thenReturn(Position.FORWARD);
    }

    @Test
    void shouldNotTransferWhenWindowInactiveOrSquadFull() {
        calendar.isTransferWindowActive = false;
        when(buyingTeam.getPlayerList()).thenReturn(List.of());

        boolean canTransferClosedWindow = transferService.isClubAbleToTransfer(calendar, buyingTeam);
        assertThat(canTransferClosedWindow).isFalse();

        calendar.isTransferWindowActive = true;
        List<Player> fullSquad = java.util.stream.Stream.generate(() -> mock(Player.class))
                .limit(25)
                .toList();
        when(buyingTeam.getPlayerList()).thenReturn(fullSquad);

        boolean canTransfer = transferService.isClubAbleToTransfer(calendar, buyingTeam);
        assertThat(canTransfer).isFalse();
    }

    @Test
    void shouldUpdateBudgetsAndSignContractOnSuccessfulTransfer() {
        calendar.isTransferWindowActive = true;
        when(buyingTeam.getPlayerList()).thenReturn(List.of());

        lenient().when(random.getRandomInt(anyInt(), anyInt())).thenReturn(0);

        when(player.getTeam()).thenReturn(sellingTeam);
        when(salaryCalculator.calculateExpectedMonthlySalary(player)).thenReturn(1000.0);

        TransferService spyService = spy(transferService);
        doReturn(java.util.Optional.of(player)).when(spyService).pickAffordablePlayer(buyingTeam, Position.FORWARD, 0);

        boolean success = spyService.doTransfer(buyingTeam, calendar, Position.FORWARD);

        assertThat(success).isTrue();
        verify(buyingTeam).subtractBudget(48_000.0);
        verify(sellingTeam).addBudget(36_000.0);
        verify(sellingTeam).removePlayer(player);
        verify(freeAgents).removeFreeAgent(player);
    }

    @Test
    void shouldIncludeYoungProspectInAffordablePlayers() {
        Player prospect = mock(Player.class);
        PlayerAttributes attributes = mock(PlayerAttributes.class);

        when(prospect.getPosition()).thenReturn(Position.FORWARD);
        when(prospect.getOverall()).thenReturn(50);
        when(prospect.getAge()).thenReturn(19);
        when(prospect.getAttributes()).thenReturn(attributes);
        when(attributes.getPotential()).thenReturn(75);

        when(freeAgents.getFreeAgents()).thenReturn(List.of(prospect));
        when(salaryCalculator.calculateExpectedMonthlySalary(prospect)).thenReturn(100.0);

        List<Player> result = transferService.getAffordablePlayers(buyingTeam, Position.FORWARD, 1);

        assertThat(result).contains(prospect);
    }

    @Test
    void shouldMovePlayerToNewTeamWhenSigningContract() {
        PlayerContract contract = mock(PlayerContract.class);

        transferService.signContract(player, contract, buyingTeam);

        verify(player).signContract(contract);
        verify(player).setTeam(buyingTeam);
        verify(buyingTeam).addPlayer(player);
    }
}
