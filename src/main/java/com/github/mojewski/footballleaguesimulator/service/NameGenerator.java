package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class NameGenerator {

    private final Map<Country, List<String>> firstNames = new EnumMap<>(Country.class);
    private final Map<Country, List<String>> lastNames = new EnumMap<>(Country.class);

    private static final List<String> DEFAULT_FIRST_NAME = List.of("Adam", "John");
    private static final List<String> DEFAULT_LAST_NAME = List.of("Smith", "Pork");

    public NameGenerator() {
       initFirstNames();
       initLastNames();
    }

    public String generateFirstName(Country country) {
        List<String> names = firstNames.getOrDefault(country, DEFAULT_FIRST_NAME);
        return getRandomElement(names);
    }

    public String generateLastName(Country country) {
        List<String> names = lastNames.getOrDefault(country, DEFAULT_LAST_NAME);
        return getRandomElement(names);
    }

    public String getRandomElement(List<String> list) {
        if(list == null || list.isEmpty()) { return "Unknown"; }

        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

    private void initFirstNames() {
        // POLAND
        firstNames.put(Country.POLAND, List.of(
                "Jan", "Piotr", "Mateusz", "Kacper", "Szymon", "Filip", "Jakub", "Michał",
                "Bartosz", "Dawid", "Tomasz", "Paweł", "Kamil", "Maciej", "Wojciech",
                "Sebastian", "Łukasz", "Karol", "Marcin", "Patryk", "Radosław", "Oskar",
                "Dominik", "Przemysław", "Damian", "Adrian", "Krystian", "Hubert", "Igor", "Nikodem"
        ));

        // ENGLAND
        firstNames.put(Country.ENGLAND, List.of(
                "Harry", "Jack", "Oliver", "George", "Charlie", "Jacob", "Thomas", "James",
                "Mason", "Jude", "Declan", "Cole", "Trent", "Marcus", "Phil", "Bukayo",
                "Kieran", "Jordan", "Callum", "Aaron", "Harvey", "Ollie", "Dominic", "Reece",
                "Conor", "Ben", "Luke", "Eberechi", "Anthony", "Jarrod"
        ));

        // SPAIN
        firstNames.put(Country.SPAIN, List.of(
                "Alejandro", "Mateo", "Pablo", "Álvaro", "Hugo", "Adrián", "David", "Lucas",
                "Gavi", "Pedri", "Lamine", "Rodri", "Nico", "Dani", "Ferran", "Mikel",
                "Unai", "Marco", "Iker", "Sergio", "Carlos", "Pau", "Marc", "Borja",
                "Aitor", "Brahim", "Yeremy", "Kepa", "Americ", "Martín"
        ));

        // GERMANY
        firstNames.put(Country.GERMANY, List.of(
                "Lukas", "Finn", "Maximilian", "Felix", "Paul", "Jonas", "Leon", "Niklas",
                "Florian", "Jamal", "Kai", "Julian", "Leroy", "Serge", "Joshua", "Thomas",
                "Timo", "Nico", "Antonio", "Emre", "Waldemar", "Maximilian", "Pascal", "Robin",
                "Alexander", "Kevin", "Jonathan", "Jan-Niklas", "David", "Angelo"
        ));

        // BRAZIL
        firstNames.put(Country.BRAZIL, List.of(
                "Gabriel", "Lucas", "Matheus", "Guilherme", "Enzo", "Rafael", "Vinícius", "Rodrygo",
                "Endrick", "Richarlison", "Raphinha", "Casemiro", "Bruno", "Douglas", "Éder",
                "Gerson", "Bento", "Sávio", "Estêvão", "Murillo", "Yan", "Vitor", "Léo",
                "Fabinho", "Fred", "Luan", "Renan", "Arthur", "Igor", "Caio"
        ));

        // ITALY
        firstNames.put(Country.ITALY, List.of(
                "Lorenzo", "Francesco", "Leonardo", "Alessandro", "Mattia", "Andrea", "Gabriele", "Matteo",
                "Gianluca", "Nicolò", "Federico", "Giacomo", "Riccardo", "Davide", "Filippo", "Marco",
                "Gianluigi", "Sandro", "Moise", "Destiny", "Wilfried", "Mateo", "Michael", "Raoul",
                "Giorgio", "Ciro", "Manuel", "Bryan", "Pietro", "Elia"
        ));

        // FRANCE
        firstNames.put(Country.FRANCE, List.of(
                "Antoine", "Kylian", "Léo", "Gabriel", "Louis", "Raphaël", "Jules", "Lucas",
                "Theo", "Ousmane", "Randal", "Bradley", "Warren", "Eduardo", "Aurelien", "Ibrahima",
                "Dayot", "William", "Marcus", "Mathys", "Rayan", "Desire", "Michael", "Khepren",
                "Enzo", "Clément", "Benjamin", "Hugo", "Adrien", "Christopher"
        ));

        // PORTUGAL
        firstNames.put(Country.PORTUGAL, List.of(
                "Bernardo", "Diogo", "João", "Martim", "Rodrigo", "Afonso", "Gonçalo", "Pedro",
                "Francisco", "Rafael", "Bruno", "Rúben", "Vitinha", "Nuno", "Gonçalo", "António",
                "Otávio", "Tiago", "Matheus", "Nelson", "Renato", "Daniel", "André", "Fabio",
                "Tomas", "Dinis", "Salvador", "Santiago", "Guilherme", "Tomas"
        ));

        // NETHERLANDS
        firstNames.put(Country.NETHERLANDS, List.of(
                "Daan", "Sven", "Sem", "Lars", "Jesse", "Bram", "Milan", "Luuk",
                "Cody", "Frenkie", "Xavi", "Memphis", "Virgil", "Nathan", "Bart", "Tijani",
                "Jeremie", "Micky", "Quinten", "Ian", "Brian", "Steven", "Teun", "Mats", "Donyell",
                "Justin", "Stefan", "Wout", "Noa", "Jorrel"
        ));

        // BELGIUM
        firstNames.put(Country.BELGIUM, List.of(
                "Kevin", "Thibaut", "Eden", "Arthur", "Noah", "Liam", "Romain", "Lucas",
                "Jérémy", "Amadou", "Loïs", "Charles", "Youri", "Alexis", "Zeno", "Johan",
                "Aster", "Maxim", "Orel", "Dodi", "Koni", "Mandela", "Arne", "Matz",
                "Thomas", "Timothy", "Jan", "Wout", "Yannick", "Leander"
        ));

        // ARGENTINA
        firstNames.put(Country.ARGENTINA, List.of(
                "Lautaro", "Thiago", "Joaquín", "Santino", "Mateo", "Benjamin", "Felipe", "Julian",
                "Alexis", "Enzo", "Rodrigo", "Cristian", "Nahuel", "Gonzalo", "Lisandro", "Exequiel",
                "Alejandro", "Valentín", "Emi", "Paulo", "Ángel", "Leandro", "Marcos", "Facundo",
                "Lucas", "Giuliano", "Nehuén", "Matías", "Claudio", "Alan"
        ));

        // URUGUAY
        firstNames.put(Country.URUGUAY, List.of(
                "Federico", "Darwin", "Facundo", "Rodrigo", "Ronald", "Mathías", "Matías", "Manuel",
                "Sebastian", "Giorgian", "Nicolás", "Luciano", "Guillermo", "Emiliano", "Brian", "Felipe",
                "Agustín", "Franco", "Joaquín", "Santi", "Cristian", "Kian", "Thiago", "Gonzalo",
                "Gaston", "Lucas", "Josema", "Diego", "Nahitan", "Maximilliano"
        ));

        // COLOMBIA
        firstNames.put(Country.COLOMBIA, List.of(
                "Santiago", "Samuel", "Jerónimo", "Emmanuel", "David", "Emiliano", "Luis", "James",
                "Jhon", "Jefferson", "Wilmar", "Mateus", "Davinson", "Yerry", "Daniel", "Camilo",
                "Richard", "Jorge", "Kevin", "Johan", "Carlos", "Yaser", "Jhonader", "Juan",
                "Piero", "Brahian", "Deiver", "Nelson", "Aries", "Yerson"
        ));

        // CROATIA
        firstNames.put(Country.CROATIA, List.of(
                "Luka", "Ivan", "Marko", "David", "Petar", "Filip", "Josip", "Mateo",
                "Joško", "Dominik", "Borna", "Lovro", "Mario", "Nikola", "Ante", "Martin",
                "Roko", "Dion", "Kristijan", "Lirim", "Gabriel", "Franjo", "Marin", "Nediljko",
                "Igor", "Mislav", "Bruno", "Toma", "Stipe", "Karlo"
        ));

        // SERBIA
        firstNames.put(Country.SERBIA, List.of(
                "Nikola", "Luka", "Stefan", "Marko", "Lazar", "Dušan", "Aleksandar", "Strahinja",
                "Filip", "Sergej", "Miloš", "Vanja", "Andrija", "Ivan", "Nemanja", "Saša",
                "Petar", "Uroš", "Kosta", "Mijat", "Veljko", "Milan", "Srđan", "Ognjen",
                "Kosta", "Jan-Carlo", "Mihajlo", "Đorđe", "Radovan", "Dejan"
        ));

        // SWITZERLAND
        firstNames.put(Country.SWITZERLAND, List.of(
                "Noah", "Luca", "Nico", "Yanick", "Jonas", "Fabian", "David", "Manuel",
                "Granit", "Breel", "Silvan", "Denis", "Ruben", "Dan", "Michel", "Gregor",
                "Yvon", "Cédric", "Renato", "Filip", "Ardon", "Kwadwo", "Vincent", "Becir",
                "Leonidas", "Aurèle", "Simon", "Ugrinic", "Steven", "Zeki"
        ));

        // AUSTRIA
        firstNames.put(Country.AUSTRIA, List.of(
                "Tobias", "Marcel", "David", "Florian", "Alexander", "Jakob", "Lukas", "Christoph", "Stefan",
                "Konrad", "Nicolas", "Patrick", "Gernot", "Maximilian", "Romano", "Phillipp", "Phillipp",
                "Leopold", "Marco", "Michael", "Flavius", "Alexander", "Muhamed", "Niklas", "Kevin",
                "Florian", "Dejan", "Matthias", "Paul", "Moritz"
        ));

        // DENMARK
        firstNames.put(Country.DENMARK, List.of(
                "Christian", "Mikkel", "Rasmus", "Frederik", "Emil", "Kasper", "Jonas", "Pierre-Emile",
                "Joachim", "Andreas", "Victor", "Alexander", "Morten", "Kasper", "Mads", "Gustav",
                "Jesper", "Philip", "Mathias", "Anders", "Magnus", "Thomas", "Yussuf", "Oliver",
                "Jacob", "Albert", "Lucas", "Oscar", "Sebastian", "Henrik"
        ));

        // NORWAY
        firstNames.put(Country.NORWAY, List.of(
                "Erling", "Martin", "Sander", "Henrik", "Tobias", "Kristian", "Markus", "Oscar",
                "Antonio", "Julian", "Leo", "Alexander", "Jørgen", "Kristoffer", "Aron", "Andreas",
                "Hugo", "Morten", "Jonas", "Fredrik", "Sindre", "Even", "Emil", "Odin",
                "Håkon", "Ola", "Mathias", "Kasper", "Sivert", "Cian"
        ));

        // SWEDEN
        firstNames.put(Country.SWEDEN, List.of(
                "Alexander", "Dejan", "Viktor", "Emil", "Hugo", "William", "Filip", "Lucas",
                "Sebastian", "Niclas", "Carl", "Isak", "Jesper", "Hjalmar", "Yasin", "Gabriel",
                "Anton", "Simon", "Samuel", "Edvin", "Daniel", "Roony", "Ken", "Ludwig",
                "Gustav", "Pontus", "Oskar", "Kristoffer", "Benjamin", "Mattias"
        ));

        // TURKEY
        firstNames.put(Country.TURKEY, List.of(
                "Arda", "Emre", "Hakan", "Yusuf", "Burak", "Kaan", "Cengiz", "Enes",
                "Kerem", "Orkun", "Ferdi", "Kenan", "Barış", "Semih", "Salih", "Cenk",
                "İrfan", "Mert", "Uğurcan", "Samet", "Ozan", "Zeki", "Ahmetcan", "Can",
                "Doğukan", "Eren", "Yunus", "Berke", "Oğuz", "Arda"
        ));

        // GREECE
        firstNames.put(Country.GREECE, List.of(
                "Georgios", "Konstantinos", "Dimitrios", "Ioannis", "Nikolaos", "Panagiotis", "Christos", "Vangelis",
                "Anastasios", "Fotis", "Giannoulis", "Konstantinos", "Petros", "Odysseas", "Sokratis", "Andreas",
                "Stefanos", "Alexandros", "Michalis", "Lazaros", "Charalampos", "Manolis", "Nectarios", "Marios",
                "Thanasis", "Athanasios", "Vasileios", "Dimitris", "Antonios", "Ilias"
        ));

        // SCOTLAND
        firstNames.put(Country.SCOTLAND, List.of(
                "Callum", "Lewis", "Finlay", "Archie", "Rory", "Kieran", "Scott", "Andrew",
                "John", "Billy", "Che", "Aaron", "Ryan", "Nathan", "Ben", "Ross",
                "Tommy", "Lennon", "Lyall", "Josh", "Elliot", "Greg", "Grant", "Liam",
                "Craig", "Stuart", "Connor", "Ewan", "Blair", "Fraser"
        ));

        // JAPAN
        firstNames.put(Country.JAPAN, List.of(
                "Kaoru", "Takefusa", "Takumi", "Wataru", "Daichi", "Kyogo", "Ritsu", "Ao",
                "Kou", "Yukinari", "Hiroki", "Keito", "Reo", "Koki", "Kenta", "Kosei",
                "Zion", "Takuma", "Mao", "Joel", "Shuto", "Kodai", "Ryotaro", "Seiya",
                "Daiki", "Sota", "Taisei", "Yuito", "Kuryu", "Tatsuki"
        ));

        // SOUTH_KOREA
        firstNames.put(Country.SOUTH_KOREA, List.of(
                "Heung-min", "Kang-in", "Min-jae", "Hee-chan", "Gue-sung", "Woo-yeong", "Hyun-gyu", "Seung-ho",
                "In-beom", "Jae-sung", "Young-woo", "Myeong-jae", "Sang-bin", "Hyun-seok", "Ji-soo", "Jun-ho",
                "Min-kyu", "Tae-seok", "Han-beom", "Seong-beom", "Ju-sung", "Seon-min", "Jin-sub", "Hyeon-woo",
                "Bum-keun", "Dong-gyeong", "Tae-hwan", "Jin-su", "Young-gwon", "Ui-jo"
        ));

        // USA
        firstNames.put(Country.USA, List.of(
                "Christian", "Weston", "Tyler", "Gio", "Brendan", "Timothy", "Walker", "Antonee",
                "Sergiño", "Yunus", "Malik", "Ricardo", "Josh", "Folarin", "Miles", "Chris",
                "Gabriel", "Joe", "Johnny", "Caden", "Jack", "Brian", "Kristoffer", "Luca",
                "Tanner", "Jalen", "Caleb", "Aidan", "Patrick", "Griffin"
        ));

        // MEXICO
        firstNames.put(Country.MEXICO, List.of(
                "Santiago", "Hirving", "Edson", "Raúl", "Guillermo", "Uriel", "César", "Johan",
                "Luis", "Orbelín", "Érick", "Julian", "Marcel", "Rodrigo", "Gilberto", "Roberto",
                "Mateo", "Sebastián", "Diego", "Gerardo", "Carlos", "Alexis", "Henry", "Jesús",
                "Ozziel", "Fidel", "Ramon", "Israel", "Bryan", "Alan"
        ));

        // NIGERIA
        firstNames.put(Country.NIGERIA, List.of(
                "Victor", "Kelechi", "Samuel", "Alex", "Wilfred", "Ademola", "Terem", "Taiwo",
                "Boniface", "Nathaniel", "Raphael", "Frank", "Calvin", "Semilore", "Stanley", "Maduka", "Francis",
                "Benjamin", "Bright", "Fisayo", "Zaidu", "Ola", "Tyronne", "Igoh", "Sadiq",
                "Chidera", "Cyriel", "Alhassan", "Akinkunmi", "Christantus"
        ));

        // SENEGAL
        firstNames.put(Country.SENEGAL, List.of(
                "Sadio", "Kalidou", "Gana", "Ismaïla", "Boulaye", "Pape", "Nicolas", "Lamine",
                "Habib", "Iliman", "Cheikhou", "Formose", "Mikayil", "Moustapha", "Habib", "Pape",
                "Dion", "Arouna", "Abdoulaye", "Bamba", "Seneny", "Mory", "Édouard", "Samba",
                "Krépin", "Nampalys", "Abdou", "Alpha", "Rasoul", "Assane"
        ));

        // MOROCCO
        firstNames.put(Country.MOROCCO, List.of(
                "Achraf", "Hakim", "Youssef", "Sofyan", "Azzedine", "Bilal", "Yassine", "Brahim",
                "Amine", "Abde", "Ilias", "Soufiane", "Amir", "Ismael", "Chadi", "Nayef",
                "Yahia", "Nousseir", "Monir", "Selim", "Oussama", "Zakaria", "Benjamin", "Ayoub",
                "Walid", "Achraf", "Oussama", "Ibrahim", "Hamza", "Anass"
        ));
    }

    private void initLastNames() {
        // POLAND
        lastNames.put(Country.POLAND, List.of(
                "Kowalski", "Nowak", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Zielonka",
                "Szymański", "Woźniak", "Kozłowski", "Jankowski", "Wojciechowski", "Kwiatkowski",
                "Krawczyk", "Piotrowski", "Grabowski", "Zając", "Pawłowski", "Michalski",
                "Jabłoński", "Król", "Majewski", "Olszewski", "Stępień", "Głowacki",
                "Lewandowski", "Zieliński", "Bednarek", "Cash", "Milik"
        ));

        // ENGLAND
        lastNames.put(Country.ENGLAND, List.of(
                "Smith", "Jones", "Taylor", "Brown", "Williams", "Wilson", "Johnson", "Walker",
                "Wright", "Robinson", "Thompson", "White", "Hughes", "Edwards", "Green",
                "Hall", "Wood", "Clarke", "Paterson", "Kane", "Pickford", "Rice",
                "Saka", "Foden", "Bellingham", "Palmer", "Gallagher", "Trippier", "Ramsdale", "Grealish"
        ));

        // SPAIN
        lastNames.put(Country.SPAIN, List.of(
                "García", "Rodríguez", "González", "Fernández", "López", "Martínez", "Sánchez",
                "Pérez", "Gómez", "Martín", "Jiménez", "Ruiz", "Hernández", "Díaz", "Moreno",
                "Álvarez", "Muñoz", "Romero", "Alonso", "Torres", "Navarro", "Ramos",
                "Carvajal", "Yamal", "Olmo", "Oyarzabal", "Simón", "Zubimendi", "Laporte", "Cucurella"
        ));

        // GERMANY
        lastNames.put(Country.GERMANY, List.of(
                "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker",
                "Schulz", "Hoffmann", "Schäfer", "Koch", "Bauer", "Richter", "Klein", "Wolf",
                "Schröder", "Neumann", "Schwarz", "Zimmermann", "Musiala", "Wirtz", "Kimmich",
                "Goretzka", "Havertz", "Gündoğan", "Rüdiger", "Schlotterbeck", "Raum", "Andrich"
        ));

        // BRAZIL
        lastNames.put(Country.BRAZIL, List.of(
                "Silva", "Santos", "Souza", "Oliveira", "Pereira", "Lima", "Ferreira", "Costa",
                "Rodrigues", "Almeida", "Nascimento", "Alves", "Carvalho", "Mendes", "Araújo",
                "Ribeiro", "Gomes", "Barbosa", "Martins", "Rocha", "Junior", "Paquetá",
                "Guimarães", "Militão", "Coutinho", "Firmino", "Neres", "Marlon", "Teixeira", "Moreira"
        ));

        // ITALY
        lastNames.put(Country.ITALY, List.of(
                "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci",
                "Marini", "Greco", "Bruno", "Gallo", "Conti", "De Luca", "Mancini", "Costa",
                "Barella", "Bastoni", "Chiesa", "Dimarco", "Frattesi", "Scamacca", "Donnarumma", "Tonali",
                "Udogie", "Retegui", "Calafiori", "Bellanova", "Cambiaso", "Rovella"
        ));

        // FRANCE
        lastNames.put(Country.FRANCE, List.of(
                "Martin", "Bernard", "Dubois", "Thomas", "Robert", "Richard", "Petit", "Moreau",
                "Laurent", "Simon", "Michel", "Lefebvre", "Leroy", "Roux", "David", "Bertrand",
                "Mbappé", "Camavinga", "Tchouaméni", "Dembélé", "Saliba", "Konaté", "Upamecano", "Thuram",
                "Zaïre-Emery", "Barcola", "Kolo Muani", "Coman", "Olise", "Nkunku"
        ));

        // PORTUGAL
        lastNames.put(Country.PORTUGAL, List.of(
                "Silva", "Santos", "Ferreira", "Pereira", "Oliveira", "Costa", "Rodrigues", "Martins",
                "Jesus", "Sousa", "Fernandes", "Lopes", "Marques", "Gomes", "Ribeiro", "Carvalho",
                "Teixeira", "Neves", "Dias", "Mendes", "Cancelo", "Dalot", "Palhinha", "Inácio",
                "Silva", "Jota", "Felix", "Neto", "Conceição", "Ramos"
        ));

        // NETHERLANDS
        lastNames.put(Country.NETHERLANDS, List.of(
                "de Jong", "Jansen", "de Vries", "van de Berg", "Bakker", "Visser", "Smit", "Meijer",
                "de Boer", "Mulder", "Bosch", "Vos", "Peters", "Hendriks", "van Dijk", "Simons",
                "Gakpo", "Reijnders", "Aké", "Frimpong", "van de Ven", "Timber", "Gravenberch", "Brobbey",
                "Koopmeiners", "Verbruggen", "Depay", "Weghorst", "Malen", "Hato"
        ));

        // BELGIUM
        lastNames.put(Country.BELGIUM, List.of(
                "Peeters", "Janssens", "Maes", "Jacobs", "Mertens", "Willems", "Claes", "Goossens",
                "Wouter", "De Smet", "Hermans", "Doku", "Onana", "Openda", "De Ketelaere", "Tielemans",
                "Saelemaekers", "Debast", "Bakayoko", "Theate", "De Winter", "Vranckx", "Castagne", "Faes",
                "Kaminski", "Casteels", "Meunier", "Lukaku", "Trossard", "Dendoncker"
        ));

        // ARGENTINA
        lastNames.put(Country.ARGENTINA, List.of(
                "Álvarez", "Gómez", "Romero", "Martínez", "Di María", "Fernández", "Díaz", "Pérez",
                "Rodríguez", "González", "Sánchez", "López", "Mac Allister", "De Paul", "Molina", "Montiel",
                "Acuña", "Otamendi", "Garnacho", "Barco", "Buendía", "Carboni", "Echeverri", "Simeone",
                "Paredes", "Pezzella", "Tagliafico", "Dybala", "Lo Celso", "Scaloni"
        ));

        // URUGUAY
        lastNames.put(Country.URUGUAY, List.of(
                "Suárez", "Cavani", "Núñez", "Valverde", "Bentancur", "Araújo", "Giménez", "Olivera",
                "Ugarte", "Pellistri", "Torres", "de Arrascaeta", "Viña", "Mele", "Caceres", "Marichal",
                "Rodríguez", "González", "Martínez", "Sánchez", "Gómez", "López", "Pérez", "Díaz",
                "Fernández", "Álvarez", "Moreno", "Romero", "Herrera", "Acosta"
        ));

        // COLOMBIA
        lastNames.put(Country.COLOMBIA, List.of(
                "Rodríguez", "Gómez", "Martínez", "García", "López", "Hernández", "Díaz", "Arias",
                "Lerma", "Sánchez", "Mina", "Muñoz", "Mojica", "Ríos", "Castaño", "Carrascal",
                "Asprilla", "Lucumí", "Cuesta", "Machado", "Borré", "Durán", "Sinisterra", "Vargas",
                "Ospina", "Montero", "Pérez", "Torres", "Quintero", "Gutiérrez"
        ));

        // CROATIA
        lastNames.put(Country.CROATIA, List.of(
                "Horvat", "Kovačević", "Babić", "Marić", "Jurić", "Novak", "Modrić", "Gvardiol",
                "Livaković", "Sosa", "Majer", "Pašalić", "Sučić", "Baturina", "Erlić", "Šutalo",
                "Stanišić", "Pjaca", "Ivanušec", "Budimir", "Kramarić", "Vlašić", "Labrović", "Ivušić",
                "Juranović", "Moro", "Pongračić", "Frigan", "Matanović", "Vušković"
        ));

        // SERBIA
        lastNames.put(Country.SERBIA, List.of(
                "Jovanović", "Petrović", "Nikolić", "Ilić", "Đorđević", "Pavlović", "Tadić", "Vlahović",
                "Milenković", "Mitrović", "Milinković-Savić", "Samardžić", "Eraković", "Ilić", "Gudelj", "Mladenović",
                "Kostić", "Lukić", "Maksimović", "Jović", "Nedeljković", "Ratkov", "Mijatović", "Babić", "Gaćinović",
                "Živković", "Spajić", "Stojić", "Simić", "Petković"
        ));

        // SWITZERLAND
        lastNames.put(Country.SWITZERLAND, List.of(
                "Müller", "Meier", "Schmid", "Keller", "Weber", "Schneider", "Frei", "Aebischer",
                "Xhaka", "Akanji", "Embolo", "Zakaria", "Vargas", "Ndoye", "Kobel", "Widmer",
                "Sommer", "Elvedi", "Rieder", "Jashari", "Duah", "Stergiou", "Amdouni", "Sierro",
                "Zuber", "Omlin", "Lotta", "Zesiger", "Sow", "Steffen"
        ));

        // AUSTRIA
        lastNames.put(Country.AUSTRIA, List.of(
                "Gruber", "Bauer", "Pichler", "Steiner", "Moser", "Mayer", "Hofer", "Alaba",
                "Sabitzer", "Laimer", "Baumgartner", "Lienhart", "Danso", "Schmid", "Wöber", "Posch",
                "Mwene", "Seiwald", "Grillitsch", "Prass", "Schmid", "Pentz", "Hedl", "Daniliuc",
                "Querfeld", "Arnautović", "Gregoritsch", "Sarkaria", "Kainz", "Wimmer"
        ));

        // DENMARK
        lastNames.put(Country.DENMARK, List.of(
                "Nielsen", "Jensen", "Hansen", "Pedersen", "Andersen", "Christensen", "Eriksen", "Højlund",
                "Damsgaard", "Højbjerg", "Andersen", "Kristiansen", "Bah", "Nelsson", "Wind", "Skov Olsen",
                "Schmeichel", "Hjulmand", "Hermansen", "Rønnow", "Dorgu", "Froholdt", "Isaksen", "Poulsen",
                "Boving", "Maehle", "Billing", "Lindstrøm", "O'Riley", "Kjaer"
        ));

        // NORWAY
        lastNames.put(Country.NORWAY, List.of(
                "Hansen", "Johansen", "Olsen", "Larsen", "Eriksen", "Berg", "Haugen", "Haaland",
                "Ødegaard", "Berge", "Ajer", "Nypan", "Bobb", "Ryerson", "Sorloth", "Strand Larsen",
                "Thorsby", "Schjelderup", "Pedersen", "Wolfe", "Møller Wolfe", "Gundersen", "Egeli", "Sahraoui",
                "Gregersen", "Selvik", "Nyland", "Daland", "Nusa", "Thorstvedt"
        ));

        // SWEDEN
        lastNames.put(Country.SWEDEN, List.of(
                "Andersson", "Johansson", "Karlsson", "Nilsson", "Eriksson", "Larsson", "Olsson", "Isak",
                "Gyökeres", "Kulusevski", "Elanga", "Lindelöf", "Cajuste", "Hien", "Bardghji", "Svanberg",
                "Holm", "Starfelt", "Augustinsson", "Forsberg", "Svensson", "Gudmundsson", "Wahlqvist", "Sema",
                "Ekdal", "Nygren", "Ayari", "Nanasi", "Krafth", "Karlström"
        ));

        // TURKEY
        lastNames.put(Country.TURKEY, List.of(
                "Yılmaz", "Kaya", "Demir", "Şahin", "Çelik", "Yıldız", "Yıldırım", "Öztürk",
                "Güler", "Kökçü", "Kadıoğlu", "Yıldız", "Alper Yılmaz", "Kılıçsoy", "Özcan", "Çalhanoğlu",
                "Kabak", "Demiral", "Akaydin", "Müldür", "Kaplan", "Elmalı", "Tosun", "Çakır",
                "Bayındır", "Aktürkoğlu", "Kahveci", "Aydın", "Yüksek", "Destan"
        ));

        // GREECE
        lastNames.put(Country.GREECE, List.of(
                "Papadopoulos", "Nikolaidis", "Georgiou", "Petridis", "Athanasiou", "Tsimikas", "Mavropanos", "Tzolis",
                "Ioannidis", "Bakasetas", "Koulierakis", "Konstantelias", "Pelkas", "Pavlidis", "Vlachodimos", "Rota",
                "Galanopoulos", "Mantalos", "Masouras", "Siopis", "Kourbelis", "Retsos", "Giannoulis", "Hatzidiakos",
                "Chatzigiovanis", "Mandas", "Zafeiris", "Ntoi", "Tzolakis", "Saliakas"
        ));

        // SCOTLAND
        lastNames.put(Country.SCOTLAND, List.of(
                "Robertson", "Campbell", "Stewart", "MacDonald", "Scott", "Murray", "McGinn", "McTominay",
                "Tierney", "Gilmour", "Hickey", "Ferguson", "Adams", "Doak", "McKenna", "Ralston",
                "McLean", "Patterson", "Hendry", "Cooper", "Clark", "Porteous", "Armstrong", "Dykes",
                "Shankland", "Nisbet", "Wright", "McCrorie", "Johnston", "Turnbull"
        ));

        // JAPAN
        lastNames.put(Country.JAPAN, List.of(
                "Sato", "Suzuki", "Takahashi", "Tanaka", "Watanabe", "Ito", "Yamamoto", "Mitoma",
                "Kubo", "Minamino", "Endo", "Kamada", "Doan", "Furuhashi", "Itakura", "Sugawara",
                "Machida", "Nakamura", "Hatate", "Maeda", "Sano", "Suzuki", "Taniguchi", "Ueda",
                "Sako", "Hosoya", "Fujita", "Sano", "Sano", "Morishita"
        ));

        // SOUTH_KOREA
        lastNames.put(Country.SOUTH_KOREA, List.of(
                "Kim", "Lee", "Park", "Choi", "Jung", "Kang", "Cho", "Yoon",
                "Jang", "Lim", "Han", "Oh", "Seo", "Shin", "Kwon", "Hwang", "Song", "An", "Hong", "Yoo",
                "Son", "Jeong", "Paik", "Yang", "Seol", "Um", "Eom", "Jo", "Bae", "Boh"
        ));

        // USA
        lastNames.put(Country.USA, List.of(
                "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Pulisic",
                "McKennie", "Adams", "Reyna", "Aaronson", "Weah", "Zimmerman", "Robinson", "Dest",
                "Musah", "Tillman", "Pepi", "Sargent", "Balogun", "Richards", "Slonina", "Scally",
                "Cardoso", "Trusty", "Lund", "DeLaTorre", "Wright", "Cowell"
        ));

        // MEXICO
        lastNames.put(Country.MEXICO, List.of(
                "Hernández", "García", "Martínez", "López", "González", "Pérez", "Rodríguez", "Giménez",
                "Lozano", "Álvarez", "Jiménez", "Ochoa", "Montes", "Vásquez", "Chávez", "Pineda",
                "Sánchez", "Quiñones", "Ruiz", "Huescas", "Alvarado", "Vega", "Martin", "Gallardo",
                "Lira", "Romo", "Guzmán", "Juárez", "Reyes", "Acevedo"
        ));

        // NIGERIA
        lastNames.put(Country.NIGERIA, List.of(
                "Osimhen", "Ndidi", "Iwobi", "Chukwueze", "Iheanacho", "Lookman", "Moffi", "Awoniyi",
                "Boniface", "Onyedika", "Onyeka", "Bassey", "Nwabali", "Okoye", "Uzoho", "Tost-Ekong",
                "Sanusi", "Aina", "Ebuehi", "Omeruo", "Sadiq", "Ejuke", "Dessers", "Yusuf",
                "Dele-Bashiru", "Onyemaechi", "Ogbu", "Uche", "Akor", "Urbanski"
        ));

        // SENEGAL
        lastNames.put(Country.SENEGAL, List.of(
                "Mané", "Koulibaly", "Sarr", "Gueye", "Dia", "Mendy", "Diallo", "Jackson",
                "Camara", "Diarra", "Diouf", "Ndiaye", "Faye", "Mendy", "Diallo", "Lopy",
                "Kouyaté", "Seck", "Cisse", "Niakhaté", "Jakobs", "Sabaly", "Diatta", "N'Diaye",
                "Diani", "Gassama", "Sow", "Gomis", "Diao", "Faye"
        ));

        // MOROCCO
        lastNames.put(Country.MOROCCO, List.of(
                "Hakimi", "Ziyech", "En-Nesyri", "Amrabat", "Ounahi", "Bounou", "Harit", "Díaz",
                "Adli", "Ezzalzouli", "Akhomach", "Rahimi", "Richardson", "Saibari", "Riad", "Aguerd",
                "Attiyat Allah", "Mazraoui", "El Kajoui", "Amallah", "El Azzouzi", "El Khannouss", "Bouchouari", "El Kaabi", "Cheddira",
                "Dari", "Abqar", "Targhalline", "Mghamimi", "Zaroury"
        ));
    }
}
