# Solutions to auditory exercises, laboratory exercises and exercises in general for the course Advanced programming year 2025/26
## src/laboratoriski


### lab1
<details>

### Problem 1
<details>
Треба да се креира апликација за банка која ќе управуваа со сметките на повеќе корисниците и ќе врши трансакции помеѓу нив. Банката работи само со долари.

За потребите на ваквата апликација треба да се напишат класите Account,Transaction и Bank. Класата Account претставува една сметка на еден корисник и треба да ги чува следните податоци:

    Име на корисникот,
    единствен идентификационен број (long)
    тековното салдо на сметката (реален број).

Оваа класа исто така треба да ги имплементира и следниве методи

    Account(String name, double balance) – конструктор со параметри (id-то треба да го генерирате сами со помош на класата java.util.Random)
    getBalance():double
    getName():String
    getId():long
    setBalance(double balance)
    toString():String – враќа стринг во следниот формат, \n означува нов ред

    Name:Andrej Gajduk\n
    Balance:20.00$\n

Класата Transaction претставува трансакција (префрлување пари од една на друга сметка), од страна на банката за што честопати се наплаќа провизија. За почеток треба да се напише класата Transaction со податочни членови за идентификационите броеви на две сметки, едната од која се одземаат парите и друга на која се додаваат парите, текстуален опис и износ на трансакцијата.

За оваа класа треба да ги имплементирате методите:

    Transaction(long fromId, long toId, Stirng description, double amount) – конструктор со параметри
    getAmount():double
    getFromId():long
    getToId():long

Оваа класа треба да е immutable, а можете и да ја направите и апстрактна бидејќи не е наменета директно да се користи туку само како основна класа за изведување на други класи.

Како што споменавме претходно банката наплаќа провизија за одредени трансакции. Има два типа на провизија, фискна сума и процент. Кај фиксна сума за било која трансакција без разлика на износот на трансакцијата се наплаќа исто провизија (пример 10$). Кај процент се пресметува процент од целиот износ (процентите се зададени како цели броеви од 1-100).

За да се прави разлика меѓу различните типови на провизија, треба да напишете уште две класи кои ќе наследуваат од Transaction кои треба да ги именувате FlatAmountProvisionTransaction и FlatPercentProvisionTransaction.

Првата класа FlatAmountProvisionTransaction треба да содржи соодветен конструктор

    FlatAmountProvisionTransaction(long fromId, long toId,double amount, double flatProvision) кој го иницијализира полето за опис на "FlatAmount" и соодветен get метод
    getFlatAmount():double

Слично и класата FlatPercentProvisionTransaction треба да има соодветен конструктор

    FlatPercentProvisionTransaction (long fromId, long toId, double amount, int centsPerDolar) кој го иницијализира полето за опис на "FlatPercent" и соодветен get метод
    getPercent():int

Исто така треба да се преоптовари equals(Object o):boolean методот и за двете класи.

За крај треба да ја имплементирате класата Bank која ги чува сметките од своите корисници и дополнително врши трансакции. Класата освен сметките на своите корисници, треба да ги чува и сопственото име и вкупната сума на трансфери како и вкупната наплатена провизија од страна на банката за сите трансакции.

Класата Bank треба да ги нуди следните методи:

    Bank(String name, Account accounts[]) – конструктор со соодветните параметри (направете сопствена копија на низата од сметки)
    makeTransaction(Transaction t):boolean – врши проверка дали корисникот ги има потребните средства на сметка и дали и двете сметки на кои се однесува трансакцијата се нависитина во банката и ако и двата услови се исполнето ја извршува трансакцијата и враќа true, во спротивно враќа false
    totalTransfers():double – ја дава вкупната сума на пари кои се префрлени во сите трансакции до сега
    totalProvision():double – ја дава вкупната провизија наплатена од банката за сите извршени трансакции до сега
    toString():String - го враќа името на банката во посебна линија во формат

    Name:Banka na RM\n
    \n

    по што следат податоците за сите корисници.

    Провизијата се наплаќа така што на основната сума на трансакцијата се додава вредноста не провизијата и таа сума се одзема од првата сметка (праќачот).

    За сите класи да се напишат соодветни equals и hashCode методи.
</details>

### Problem 2
<details>Да се дефинира интерфејс Movable што ќе ги дефинира основните својства на еден движечки објект:

    движење нагоре (void moveUp())
    движење надолу (void moveLeft())
    движење надесно (void moveRight())
    движење налево (void moveLeft())
    пристап до моменталните x,y координати на објектот (int getCurrentXPosition() и int getCurrentYPosition()).

Постојат два типа на движечки објекти: движечка точка (MovingPoint) и движечки круг (MovingCircle). Да се дефинираат овие две класи коишто го имплементираат интерфејсот Movable.

Во класата MovingPoint се чуваат информации за:

    x и y координати (цели броеви)
    xSpeed и ySpeed : степенот на поместување на движечката точка во x насока и y насока (цели броеви)

За класата да се имплементираат:

    конструктор со аргументи: MovablePoint(int x, int y, int xSpeed, int ySpeed),
    методите наведени во интерфејсот Movable
    toString метод кој дава репрезентација на објектите во следнот формат Movable point with coordinates (5,35)

Во класата MovingCircle се чуваат информации за:

    радиусот на движечкиот круг (цел број)
    центарот на движечкиот круг (објект од класата MovingPoint).

За класата да се имплементираат:

    конструктор со аргументи: MovableCircle(int radius, MovablePoint center)
    методите наведени во интерфејсот Movable
    toString метод којшто дава репрезентација на објектите во следниот формат Movable circle with center coordinates (48,21) and radius 3

Првите четири методи од Movable (moveUp, modeDown, moveRight, moveLeft) треба да фрлат исклучок од тип ObjectCanNotBeMovedException доколку придвижувањето во соодветната насока не е возможно, односно со придвижувањето се излегува од дефинираниот простор во класата MovablesCollection. При движење на објекти од тип MovableCircle се смета дека кругот излегол од просторот, доколку неговиот центар излезе од просторот. Дозволено е дел до кругот да излезе од просторот, се додека центарот е се уште во просторот. Справете се со овие исклучоци на соодветните места. Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото.

Да се дефинира класа MovablesCollection во која што ќе се чуваат информации за:

    низа од движечки објекти (Movable [] movable)
    статичка променлива за максималната вредност на координатата X (минималната е предодредена на 0)
    статичка променлива за максималната вредност на координатата Y (минималната е предодредена на 0)

За класата да се имплементираат следните методи:

    конструктор MovablesCollection(int x_MAX, int y_MAX)
    void addMovableObject(Movable m) - метод за додавање на движечки објект во колекцијата од сите движечки објекти. Пред да се додади објектот, мора да се провери дали истиот е може да се вклопи во дефинираниот простор, односно истиот да не излегува од границите 0-X_MAX за x координатата и 0-Y_MAX за y координатата. Доколку станува збор за движечки круг, потребно е целиот круг да се наоѓа во наведениот интервал на вредности. Доколку движечкиот објект не може да биде вклопен во просторот, да се фрли исклучок од тип MovableObjectNotFittableException. Потребно е да се справите со исклучокот на соодветното место во main методот. Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото.
    void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction)- метод за придвижување на движечките објекти од тип type во насока direction. TYPE и DIRECTION се енумерации кои се задедени во почетниот код. Во зависност од насоката зададена во аргументот, да се повика соодветниот метод за придвижување.
    toString() - метод кој дава репрезентација на колекцијата од движечки објекти во следниот формат: Collection of movable objects with size [големина на колекцијата]: , по што во нов ред следуваат информации за сите движечки објекти во колекцијата.
</details>

### Problem 3
<details>Дадени ви се следниве класи:

Класа Doctor

    Преставува еден доктор со основните информации за него: бројот на лиценцата, неговото име, ниво на експертиза (1-10), број на пациенти
    Доколку нивото на експертиза е 10, се смета дека докторот е Chief.
    Имплементиран е toString методот кој го печати докторот во читлив формат (име, број на лиценца, специјализација, број на пациенти и доколку е со највисоко ниво на експертиза се печати и [Chief])
    При промена на нивото на експертиза, вредноста мора да се движи во рамките од 1-10 и не смее да биде помала од претходната

Класа EmergencyRoom

    репрезентира еден ургентен центар во една болница и содржи информации за: името на болницата, медицински персонал (низа од објекти Doctor), капацитет
    Имплементирани се следниве методи: treat, forEach, count, findFirst, filter, mapToLabels, mutate, conditionalMutate, countForEvaluation, evaluate

    treat(Supplier<Doctor> supplier) - додава доктор во ургентниот центар, доколку има слободно место
    forEach(Consumer<Doctor> action) - применува зададена акција (Consumer) врз секој доктор во низата (пример: печатење)
    count(Predicate<Doctor> condition) - го враќа бројот на доктори кои го исполнуваат дадениот услов
    findFirst(Predicate<Doctor> condition) - го враќа првиот доктор кој исполнува даден услов
    filter(Predicate<Student> condition - Враќа нова низа која ги содржи само докторите кои го исполнуваат условот.
    mapToLabels(Function<Student, String> mapper) - Враќа низа од текстуални описи, добиени со трансформирање на секој доктор со дадената функција.
    mutate(Consumer<Student> mutator) - Применува промена на сите доктори (на пример, зголемување на нивото на експертиза).
    conditionalMutate(Predicate<Student> condition, Consumer<Student> mutator) - Ја применува промената само на докторите кои го исполнуваат дадениот услов.
    countForEvaluation(DoctorEvaluator evaluator) - Користи DoctorEvaluator за да изброи колку доктори исполнуваат еден услов
    evaluate(DoctorEvaluator evaluator) - Враќа нова низа која ги содржи сите доктори кои исполнуваат услов поставен со DoctorEvaluator
    toString() - Враќа текстуален опис на ургентниот центар, кој ги содржи името на болницата, бројот на доктори кои моментално работат во него и списокот од истите.

Од ваша страна потребно е да:

    Креирате функциски интерфејс DoctorEvaluator кој ќе има еден метод: boolean evaluate(Doctor doctor);
    Да креирате класа HighExpertiseEvaluator кој ќе враќа TRUE само доколку докторот има ниво на експертиза поголем или еднаков на 7.
    Да ги разрешите барањата во main делот:
        Отворете Scanner и прочитајте цел број n што го означува бројот на доктори кои ќе се внесат.
        Креирајте Supplier<Student> кој чита податоци за еден доктор од конзолата (број на лиценца, име, ниво на експертиза и број на пациенти) и враќа нов објект Doctor.
        Додадете n доктори во користејќи го методот treat.
        Користете Consumer<Student> заедно со forEach за да ги испечатите сите доктори кои работат моментално во ургентниот центар.
        Искористете ги креираните функциски интерфејси за да одредите кои доктори:
            имаат повеќе од 20 пациенти
            имаат повисоко ниво на експертиза (7+)
            Комбинирајте ги двете состојби од функциските интерфејси и искористете го методот evaluate од класата EmergencyRoom за да ги прикажеш само тие доктори.
        Користете findFirst за да го пронајдите и прикажете Chief докторот во ургентниот центар.
        Користете mutate за да и го зголемите нивото на експертиза на сите доктори за 1.
        Користете conditionalMutate за да ја зголемите експертизата за 1 само на докторите со повеќе од 30 пациенти.
        Користете mapToLabels за да ги трансформирате сите доктори во текстуални описи и испечати ги.
        На крај, испечатете ги сите информации за ургентниот центар со користење на методот toString.

</details>

</details>

### lab2
<details>

### Problem 1
<details>Довршете ги методите</details>

### Problem 2
<details>LocalDateTime</details>

### Problem 3
<details>LocalTime API</details>

### Problem 4
<details>Да се напише класа ResizableArray која ќе претставува поле (низа) со променлива должина. Класата може да чува елементи од било кој тип (треба да биде генеричка со еден параметар T) и треба да ги има дефинирано следните методи:

    ResizableArray() - креира ново празно поле
    addElement(T element) - додава нов елемент во полето (доколку нема доволно место го зголемува капацитетот на полето).
    removeElement(T element):boolean - aко постои таков елемент истиот го брише и враќа true, во спротивно враќа false, доколку има повеќе инстанци од дадениот елемент се брише само една од нив (ако има многу празно место во полето го намалува неговиот капацитет)
    contains(T element):boolean - враќа true доколку во полето постои дадениот елемент
    toArray():Object[] - ги враќа сите елементи во полето како обична низа
    isEmpty() - враќа true доколку во полето нема ниеден елемент
    count():int - го браќа бројот на елементи во полето
    elementAt(int idx):T - го враќа елементот на соодветната позиција, доколку нема таков фрла исклучок ArrayIndexOutOfBoundsException (елементите во полето се наоѓаат на позиции [0, count()])

    Забелешка: за чување на елементите мора да се користи обична низа Т[] elements, не смее да се користи ArrayList<T> и истата мора да биде декларирана како private.

    Дополнително, класата ResizableArray треба да има еден статички метод:

    <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src)

    Овој метод треба да изврши копирање на сите елементи од src во dest (src останува непроменета, dest ги содржи сите елементи кои ги имал од порано и дополнително сите елементи кои ги има во src).

    Следно треба да се напише класа IntegerArray која наследува од класата ResizableArray IntegerArray extends ResizableArray<Integer> и служи за чување на цели броеви. Оваа класа ги нуди следниве методи:

    sum():double - ја враќа сумата на сите елементи во полето
    mean():double - го дава просекот на сите елементи во полето
    countNonZero():int - го дава бројот на елементи во полето кои имаат вредност различна од нула
    distinct():IntegerArray - враќа нов објект кој во себе ги содржи истите елементи кои ги содржи this, но нема дупликат елементи
    increment(int offset):IntegerArray - враќа нов објект кој во себе ги содржи сите елемeнти кои ги содржи this, но на нив додавајќи offset
</details>

### Problem 5
<details>Треба да се развие класа Timestamp која претставува пар на објекти од кои едниот е секогаш од тип LocalDateTime, а другиот објект е од генеричкиот тип T. Класата Timestamp ги нуди следниве функционалности:

    Timestamp(LocalDateTime time, T element) - конструктор
    getTime():LocalDateTime
    getElement():T
    compareTo(Timestamp<?> t):int - споредувањето се прави само врз основа на времињата
    equals(Object o):boolean - враќа true ако се исти времињата
    toString() :String - враќа стринг репрезентација со времето (toString) и елементот во формат time element

Забелешка: двете променливи time и element мора да бидат обележани како final.

Класата Timestamp сега треба да се искористи за да се развие класа Scheduler. Оваа класа чува повеќе објекти од класата Timestamp и исто така има еден генерички параметар T кој всушност се однесува на типот на објект кој се наоѓа во Timestamp. Класата Scheduler треба да ги имплементира следниве методи:

    Scheduler() - креира нов празен распоредувач
    add(Timestamp<T> t) - додава нов објект во распоредувачот
    remove(Timestamp<T> t):boolean - го брише соодветниот елемент од распоредувачот доколку постои и враќа true, во спротивно враќа false
    next():Timestamp<T> - го враќа следниот Timestamp објект, односно тој објект чие што време е најблиску до тековното (сега) и сѐ уште НЕ е поминато
    last():Timestamp<T> - го враќа објектот кој има време најблиску до тековното (сега) и веќе E поминат
    getAll(LocalDateTime begin, LocalDateTime end):List<Timestamp<T>> - враќа листа на настани чии времиња се наоѓаат помеѓу begin и end (не вклучувајќи ги begin и end).
</details>

### Problem 6
<details>Треба да се развие класа Queuе која претставува податочна структура ред, a во позадина e имплементирана како поврзана листа. Прво треба да се напише класа за еден елемент во листата (еден јазел) Node. Kласаta Node треба да има еден генерички параметар Т кој се однесува на елементот во јазелот и една референца кон следниот јазел во листата. Поформално класата Node треба да ги нуди следниве методи:

    Node(T element, Node<T> next) - конструктор кој ги иницијализира двете променливи
    getElement():T
    getNext():Node<T>
    setNext(Node<T> next)

Користејќи ја класата Node ја пишуваме класата Queue со следниве методи:

    Queue() – креира нов празен ред
    isEmpty():boolean - враќа true, ако редот е празен (не содржи ниеден елемент)
    enqueue(T element) - го додава елементот на крајот на редот
    dequeue():T - го отстранува елементот на почеток од редот и истиот го враќа, доколку редот е празен фрла исклучок EmptyQueueException
    peek():T - го враќа елементот на почетокот од редот (не ја менува листата), доколку редот е празен фрла исклучок EmptyQueueException
    inspect():T - го враќа елементот на крајот на редот (не ја менува листата), доколку редот е празен фрла исклучок EmptyQueueException
    count():int - го враќа бројот на елементи во редот

Забелешка: Класата Queue има еден генерички параметар кој се однесува на типот не елементи кои се чуваат во редот.

Важно: Не смее да се користат готови податочни структури како ArrayList или LinkedList, за да се имплементира класата Queue.</details>
</details>

## src/auditoriski

## src/zadachi_za_vezhbanje (execises_for_midterms)
<details>

### Zad1
<details>

Квалификациска за прв колоквиум

Да се дефинира класа ShapesApplication во која се чуваат податоци за повеќе прозорци на кои се исцртуваат геометриски слики во форма на квадрат.

За класата да се дефинира:

    ShapesApplication() - конструктор
    int readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат квадрати. Во секој ред од потокот е дадена информација за еден прозорец во формат: canvas_id size_1 size_2 size_3 …. size_n, каде што canvas_id е ИД-то на прозорецот, а после него следуваат големините на страните на квадратите што се исцртуваат во прозорецот. Методот треба да врати цел број што означува колку квадрати за сите прозорци се успешно прочитани.
    void printLargestCanvasTo (OutputStream outputStream) - метод којшто на излезен поток ќе го испечати прозорецот чии квадрати имаат најголем периметар. Печатењето да се изврши во форматот canvas_id squares_count total_squares_perimeter.
____
    INPUT:
    364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
    0469e20f 26 14 14 28 37 14 36 30
    33f2c7c0 18 12 14 38 28 26 17 22 33 36 28 33 36 38

    RESULT:
    ===READING SQUARES FROM INPUT STREAM===
    36
    ===PRINTING LARGEST CANVAS TO OUTPUT STREAM===
    364fbe94 14 1556

</details>

### Zad2
<details>

    Прв колоквиум
    
    Да се дефинира класа ShapesApplication чување на податоци за повеќе прозорци на кои и се сцртуваат геометриски слики во различна форма (квадрати и кругови)..
    
    За класата да се дефинира:

        ShapesApplication(double maxArea) - конструктор, каде maxArea е најголемата дозволена плоштина на секоја форма поединечно, која може да биде исцртана на прозорците.
        void readCanvases (InputStream inputStream) - метод којшто од влезен поток на податоци ќе прочита информации за повеќе прозорци на кои се исцртуваат различните геометриски слики. Во секој ред се наоѓа информација за еден прозорец во формат: canvas_id type_1 size_1 type_2 size_2 type_3 size_3 …. type_n size_n каде што canvas_id е ИД-то на прозорецот, a после него следуваат информации за секоја форма во прозорецот. Секоја форма е означена со карактер што го означува типот на геометриската слика (S = square, C = circle) и со големината на страната на квадратот, односно радиусот на кругот.
        При додавањето на геометриските слики на прозорецот треба да се спречи креирање и додавање на прозорец во кој има форма што има плоштина поголема од максимално дозволената. Како механизам за спречување треба да се користи исклучок од тип IrregularCanvasException (фрлањето на исклучокот не треба да го попречи вчитувањето на останатите прозорци и геометриски слики. Да се испечати порака Canvas [canvas_id] has a shape with area larger than [max_area].
        void printCanvases (OutputStream os) - метод којшто на излезен поток ќе ги испечати информациите за сите прозорци во апликацијата. Прозорците да се сортирани во опаѓачки редослед според сумата на плоштините на геометриските слики во нив. Секој прозорец да е испечатен во следниот формат: ID total_shapes total_circles total_squares min_area max_area average_area.

    За вредноста на PI користете ja константата Math.PI. За постигнување на точност со тест примерите користете double за сите децимални променливи.

-----

    Define a class ShapesApplication whre you'll keep information about multiple windows on which geometric images (in different shape - square and circle) are drawn.
    
    For the class you need to define and implement:

        ShapesApplication(double maxArea) - constructor with one argument which represents the maximum allowed area of a shape that can be drawn on the windows.
        void readCanvases (InputStream inputStream) - method that will read info about multiple windows from input stream. Each line of the data stream represents one window and it's in the format canvas_id type_1 size_1 type_2 size_2 type_3 size_3 …. type_n size_n where canvas_id is the ID of the window and after the ID there are unknown number of pairs of data for the shapes. Each pair has its type (character S = square, C = circle) and the side of the side of the square or the size of the radius of the circle.
        When adding the geometric images on the window, the creation and addition of a window which contains a shape with area greater than the maximum area, should not be allowed. This should be done via exception of type InvalidCanvasException. Throwing an exception of this type should not stop the reading of the data from the input stream. When catching the exception, the following message should be printed: Canvas [canvas_id] has a shape with area larger than [max_area].
        void printCanvases(OutputStream os) - method that will print to output stream the information for all the windows in the application. The windows should be sorted in descending order by the sum of the areas of the geometric shapes in them. Each window should be printed in the following format: ID total_shapes total_circles total_squares min_area max_area average_area.

    For the value of PI use Math.PI. Use double for better precision of the decimal numbers.
___
    INPUT
    0cc31e47 C 27 C 13 C 29 C 15 C 22
    5960017f C 30 S 15 S 588 C 25 C 14 S 14 S 17 C 19
    8ed50a65 C 29 S 12 C 13 S 30 C 25 S 11
    201c295e C 27 C 13 C 14 C 11 S 18 C 12
    184ef1d4 S 28 S 26 S 2001 S 28 C 30 C 16 S 18
    c4b48d9f S 26 C 18 C 18 S 16 S 12 C 29 S 19
    5e28f402 C 24 C 28 C 14 C 25 S 11 S 22 S 10 S 19 S 20 S 11 C 29
    91a5b09b C 30 S 10 S 28 S 10 S 18 C 28 S 14 S 10 S 30 C 21 C 24
    36e77dad C 29 S 11 S 25 S 30 C 21 C 17 S 400 S 30 S 23
    13343cb0 S 21 C 29 C 14 C 30 C 12
    
    RESULT
    ===READING CANVASES AND SHAPES FROM INPUT STREAM===
    Canvas 5960017f has a shape with area larger than 10000.00
    Canvas 184ef1d4 has a shape with area larger than 10000.00
    Canvas 36e77dad has a shape with area larger than 10000.00
    ===PRINTING SORTED CANVASES TO OUTPUT STREAM===
    5e28f402 11 5 6 100.00 2642.08 1007.35
    91a5b09b 11 4 7 100.00 2827.43 999.04
    0cc31e47 5 5 0 530.93 2642.08 1538.12
    13343cb0 5 4 1 441.00 2827.43 1395.73
    8ed50a65 6 3 3 121.00 2642.08 1050.25
    c4b48d9f 7 3 4 144.00 2642.08 873.55
    201c295e 6 5 1 324.00 2290.22 765.57


</details>

### Zad3
<details>

    Прв колоквиум (+ composite design pattern)

    Потребно е да се дефинира апликација за едноставен датотечен систем во којшто ќе се чуваат објекти коишто репрезентираат фајлови/датотеки (објекти коишто го имплементираат интерфејсот IFile).

    Да се декларира интерфејсот IFile со соодветни методи, така што секој фајл/датотека ќе ги има следните карактеристики:

    може да се пристапи до неговото име (String getFileName())
    може да се добие неговата големина во long (long getFileSize())
    може да се добие String репрезентација на фајлот (String getFileInfo(???))
    може да се сортира датотеката доколку е колекција од датотеки според големините на датотеките кои ги содржи (void sortBySize())
    може да се пресмета големината на најголемата обична датотека во датотеката (findLargestFile ())
    
    Постојат два типа на фајлови: File (обична датотека) и Folder (директориум/фолдер). Потребно е овие две класи да го имплементираат интерфејсот IFile.
    
    За еден File се чуваат информации за неговото име и големина (во long).
    
    Во класата Folder се чуваат исти информации како и за File, a дополнително се чува и листа од фајлови (и обични и директориуми). За оваа класа да се имплементираат методите:

    void addFile (IFile file) - метод за додавање на било каква датотека во листата од датотеки.
    Доколку веќе постои датотека со исто име како името на датотеката што се додава како аргумент на методот, да се фрли исклучок од тип FileNameExistsException во којшто се проследува името кое веќе постои.

    И во двете класи да се имплементираат методите коишто се декларирани во интерфејсот IFile. Да се запази на следните фактори:

    големината на еден Folder е сума од големините на сите датотеки (обични или директориуми) коишто се наоѓаат во него.
    при генерирање на String репрезентација на директориумите, датотеките и поддиректориумите во тој директориум да се вовлечени со таб ("\t").
    String репрезентацијата на една обична датотека е File name [името на фајлот со 10 места порамнето на десно] File size: [големината на фајлот со 10 места пораменета на десно ]
    String репрезентацијата на еден директориум е Folder name [името на директориумот со 10 места порамнето на десно] Folder size: [големината на директориумот со 10 места пораменета на десно ]
    возможно е сортирање само во рамки на директориум, каде што сите датотеки во тој директориум потребно е да се сортираат според големина во растечки редослед.
    методот getLargestFile() треба да ја врати големината на најголемата обична датотека во рамки на датотеката каде што е повикан.
    кога се повикува методот sortBySize() кај директориум истиот треба да се повика и за сите негови подиректориуми

    Да се дефинира класа FileSystem во која што ќе се чува само еден директориум (rootDirectory). За класата да се имплементираат:

    default конструктор FileSystem()
    void addFile (IFile file) - метод за додавање на било каква датотека во root директориумот.
    long findLargestFile () - метод којшто ја враќа големината на најголемата (обична) датотека во root директориумот.
    void sortBySize() - метод којшто ги сортира датотеките во root директориумот ( и обични и директориуми) според нивната големина во root директориумот во растечки редослед.

______

    INPUT:
    test
    3
    0
    test 12000
    0
    test 123
    0
    test_1 1070
    RESULT:
    ===READING FILES FROM INPUT===
    There is already a file named test in the folder test
    ===PRINTING FILE SYSTEM INFO===
    Folder name:       root Folder size:      13070
    Folder name:       test Folder size:      13070
    File name:       test File size:      12000
    File name:     test_1 File size:       1070
    
    ===PRINTING FILE SYSTEM INFO AFTER SORTING===
    Folder name:       root Folder size:      13070
    Folder name:       test Folder size:      13070
    File name:     test_1 File size:       1070
    File name:       test File size:      12000
    
    ===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===
    12000    

</details>

</details>




