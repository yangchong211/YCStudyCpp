//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
using namespace std;

//8.1.1.1 为何设计结构
void test8_1_1_1();
//8.1.1.2 定义结构
void test8_1_1_2();
//8.1.1.3 访问结构成员
void test8_1_1_3();
//8.1.2.1 结构作为函数参数
void test8_1_2_1();
//8.1.2.2 指向结构的指针
void test8_1_2_2();
//8.1.2.3 typedef结构方式
void test8_1_2_3();
//8.1.2.4 . 与 -> 运算符
void test8_1_2_4();
//8.1.2.5 结构作为函数返回值
void test8_1_2_5();
//结构作为函数参数
void printBook(struct Books book);
void printBook2(struct Books *book);
//8.2.1.1 类概述
void test8_2_1_1();
//8.2.1.2 类的声明与定义
void test8_2_1_2();
//8.2.1.3 类的实现
void test8_2_1_3();
//8.2.1.4 对象的声明
void test8_2_1_4();
//8.2.2.1 构造函数概述
void test8_2_2_1();
//8.2.2.3 析构函数
void test8_2_2_3();
//8.2.2.4 拷贝构造函数
void test8_2_2_4();
//8.2.3.1 类访问修饰符介绍
void test8_2_3_1();
//8.2.3.2 访问修饰符继承中的特点
void test8_2_3_2();
//8.3.1.1 访问类成员
void test8_3_1_1();
//8.3.1.2 成员函数
void test8_3_1_2();
//8.3.1.3 内联成员函数
void test8_3_1_3();
//8.3.1.4 友元函数
void test8_3_1_4();
//8.3.1.5 静态类成员
void test8_3_1_5();
//8.3.2.1 隐藏的this指针
void test8_3_2_1();
//8.3.2.2 指向类的指针
void test8_3_2_2();



int main() {
//    test8_1_1_1();
//    test8_1_1_2();
//    test8_1_1_3();
//    test8_1_2_1();
//    test8_1_2_2();
//    test8_1_2_3();
//    test8_1_2_4();
    test8_1_2_5();
    test8_2_1_1();
    test8_2_1_2();
    test8_2_1_3();
    test8_2_1_4();
    test8_2_2_1();
    test8_2_2_3();
    test8_2_2_4();
    test8_2_3_1();
    test8_2_3_2();
    test8_3_1_1();
    test8_3_1_2();
    test8_3_1_3();
    test8_3_1_4();
    test8_3_1_5();
    test8_3_2_1();
    test8_3_2_2();
    return 0;
}

void test8_1_1_1(){
    //C/C++ 数组允许定义可存储相同类型数据项的变量，但是结构是 C++ 中另一种用户自定义的可用的数据类型，它允许您存储不同类型的数据项。
    //结构用于表示一条记录，假设您想要跟踪图书馆中书本的动态，您可能需要跟踪每本书的下列属性：
    //Title ：标题
    //Author ：作者
    //Subject ：类目
    //Book ID ：书的 ID
}

struct Books{
    char title[50];
    string author;
    char subject[50];
    int book_id;
    char parity;
};

void test8_1_1_2(){
    //为了定义结构，您必须使用 struct 语句。
    //struct 语句定义了一个包含多个成员的新的数据类型，struct 语句的格式如下：
}

void test8_1_1_3(){
    Books Book1;        // 定义结构体类型 Books 的变量 Book1
    // Book1 详述
    strcpy( Book1.title, "C++ 教程");
    Book1.author = "yangchong";
    strcpy( Book1.subject, "编程语言");
    Book1.book_id = 12345;

    // 输出 Book1 信息
    cout << "第一本书标题 : " << Book1.title <<endl;
    cout << "第一本书作者 : " << Book1.author <<endl;
    cout << "第一本书类目 : " << Book1.subject <<endl;
    cout << "第一本书 ID : " << Book1.book_id <<endl;
    cout << "\n";
}

void test8_1_2_1(){
    Books book1 = Books();
    book1.book_id = 12345;
    //book1.subject = subject.c_str();
    strcpy( book1.subject, "编程语言");
    book1.author = "yangchong";
    strcpy( book1.title, "C++ 教程");
    book1.parity = 'N';
    printBook(book1);
    cout << "\n";
}

void test8_1_2_2(){
    Books Book1;
    strcpy( Book1.title, "C++ 教程");
    Book1.author = "yangchong";
    strcpy( Book1.subject, "编程语言");
    Book1.book_id = 12345;
    // 通过传 Book1 的地址来输出 Book1 信息
    printBook2(&Book1);
    cout << "\n";
}

typedef struct Books2{
    char  title[50];
    char  author[50];
    char  subject[100];
    int   book_id;
} BooksYc;

void test8_1_2_3(){
//    Books Book1, Book2;
    BooksYc Book1, Book2;
}

//结构作为函数参数
//您可以把结构作为函数参数，传参方式与其他类型的变量或指针类似。您可以使用上面实例中的方式来访问结构变量：
void printBook( struct Books book){
    cout << "书标题 : " << book.title <<endl;
    cout << "书作者 : " << book.author <<endl;
    cout << "书类目 : " << book.subject <<endl;
    cout << "书 ID : " << book.book_id <<endl;
}

//该函数以结构指针作为参数
void printBook2(struct Books *book) {
    cout << "书标题2  : " << book->title << endl;
    cout << "书作者2 : " << book->author << endl;
    cout << "书类目2 : " << book->subject << endl;
    cout << "书 ID2 : " << book->book_id << endl;
}

void test8_1_2_4() {
    //. 与 -> 运算符
    //.（点）运算符和 ->（箭头）运算符用于引用类、结构和共用体的成员: 点运算符应用于实际的对象。箭头运算符与一个指向对象的指针一起使用。
    struct Employee {
        char first_name[16];
        int  age;
    } emp;
    Employee *p_emp;
    //. 点运算符
    strcpy(emp.first_name, "zara");
    //-> 箭头运算符     -> 称为箭头运算符，它是由一个减号加上一个大于号组成。
    strcpy(p_emp->first_name, "zara");
    //访问结构的成员时使用点运算符，而通过指针访问结构的成员时则使用箭头运算符。
}

struct test{
    int i;
    char c;
    double d;
    float f;
};

struct test set( int a, float b, char c, double d ){
    struct test t;
    t.i = a;
    t.f = b;
    t.c = c;
    t.d = d;
    return t;
}

void print(struct test t2){
    printf("int:%d\n",t2.i);
    printf("char:%c\n",t2.c);
    printf("float:%f\n",t2.f);
    printf("double:%lf\n",t2.d);
}
void test8_1_2_5(){
    struct test info;
    info = set(2,3.22,'d',4.335);
    print(info);
}

void test8_2_1_1(){
    //C++ 在 C 语言的基础上增加了面向对象编程，C++ 支持面向对象程序设计。类是 C++ 的核心特性，通常被称为用户定义的类型。
    //类用于指定对象的形式，是一种用户自定义的数据类型，它是一种封装了数据和函数的组合。
    //类中的数据称为成员变量，函数称为成员函数。
    //类可以被看作是一种模板，可以用来创建具有相同属性和行为的多个对象。
}

void test8_2_1_2(){
    //定义一个类需要使用关键字 class，然后指定类的名称，并类的主体是包含在一对花括号中，主体包含类的成员变量和成员函数。
    //定义一个类，本质上是定义一个数据类型的蓝图，它定义了类的对象包括了什么，以及可以在这个对象上执行哪些操作。
    class Box{
    public:
        double length;   // 盒子的长度
        double breadth;  // 盒子的宽度
        double height;   // 盒子的高度
    };
    //关键字 public 确定了类成员的访问属性。在类对象作用域内，公共成员在类的外部是可访问的。
    //您也可以指定类的成员为 private 或 protected，这个我们稍后会进行讲解。
}

//8.2.1.3 类的实现
void test8_2_1_3(){
    class Person {
    private:
        std::string name;
        int age;
    public:
        //构造函数
        Person();
        Person(std::string a , int b){
            name = a;
            age = b;
        }
        //成员函数
        void displayInfo() {
            std::cout << "Name: " << name << std::endl;
            std::cout << "Age: " << age << std::endl;
        }
    };
    // 创建一个Person对象
    Person person("打工充", 30);
    // 调用成员函数
    person.displayInfo();
}

//8.2.1.4 对象的声明
void test8_2_1_4() {
    class Box{
    public:
        double length;   // 盒子的长度
        double breadth;  // 盒子的宽度
        double height;   // 盒子的高度
    };
    Box Box1;          // 声明 Box1，类型为 Box
    Box Box2;          // 声明 Box2，类型为 Box
}


class Line{
public:
    //构造函数
    Line();
    Line(double len);
    //在类定义中定义的成员函数把函数声明为内联的，即便没有使用 inline 标识符。
    inline void setLength(double len) {
        length = len;
    }
    double getLength();
    ~Line(){
        cout << "8.2.2 构造函数 Object is being deleted" << endl;
    }
private:
    double length;
};

// 成员函数定义，包括构造函数
Line::Line(void) {
    cout << "8.2.2.1 构造函数概述 Object is being created" << endl;
}

Line::Line(double len) {
    length = len;
}

double Line::getLength() {
    return length;
}

//8.2.2.1 构造函数概述
void test8_2_2_1() {
    //类的构造函数是类的一种特殊的成员函数，它会在每次创建类的新对象时执行。
    //构造函数的名称与类的名称是完全相同的，并且不会返回任何类型，也不会返回 void。构造函数可用于为某些成员变量设置初始值。
    Line line;
    line.setLength(10);
    cout << "8.2.2.1 构造函数概述 length: " << line.getLength() << endl;
    Line line1 = Line();
    line1.setLength(12);
    cout << "8.2.2.1 构造函数概述 length: " << line1.getLength() << endl;
    Line line3(15);
    cout << "8.2.2.2 带参数的构造函数 length: " << line3.getLength() << endl;
}


//8.2.2.3 析构函数
void test8_2_2_3() {
    //类的析构函数是类的一种特殊的成员函数，它会在每次删除所创建的对象时执行。
    //析构函数的名称与类的名称是完全相同的，只是在前面加了个波浪号（~）作为前缀，它不会返回任何值，也不能带有任何参数。
    //析构函数有助于在跳出程序（比如关闭文件、释放内存等）前释放资源。

    Line line;
    // 设置长度
    line.setLength(6.0);
    cout << "Length of line : " << line.getLength() <<endl;
}

class LineClone {
public:
    int getLength(void );
private:
    int *ptr;
    int age;
};

int getLength() {
    return 0;
}



//8.2.2.4 拷贝构造函数
//https://www.runoob.com/cplusplus/cpp-copy-constructor.html
void test8_2_2_4() {
    //拷贝构造函数是一种特殊的构造函数，它在创建对象时，是使用同一类中之前创建的对象来初始化新创建的对象。拷贝构造函数通常用于：
    //通过使用另一个同类型的对象来初始化新创建的对象。
    //复制对象把它作为参数传递给函数。
    //复制对象，并从函数返回这个对象。


}


class BoxD {
public:
    //公有成员在程序中类的外部是可访问的。您可以不使用任何成员函数来设置和获取公有变量的值
    double length;
    void setWidth(double wid);
    double getWidth(void);
private:
    //私有成员变量或函数在类的外部是不可访问的，甚至是不可查看的。只有类和友元函数可以访问私有成员。
    double width;
protected:
    //protected（受保护）成员变量或函数与私有成员十分相似，但有一点不同，protected（受保护）成员在派生类（即子类）中是可访问的。
    double height;
};

// 成员函数定义
double BoxD::getWidth(void) {
    return width;
}

void BoxD::setWidth(double wid) {
    width = wid;
}

// SmallBox 是派生类
class SmallBox:BoxD{
public:
    void setSmallHeight( double wid );
    double getSmallHeight( void );
};

// 子类的成员函数
double SmallBox::getSmallHeight(void){
    return height ;
}

void SmallBox::setSmallHeight( double wid ){
    height = wid;
}

//8.2.3.1 类访问修饰符介绍
void test8_2_3_1() {
    //数据封装是面向对象编程的一个重要特点，它防止函数直接访问类类型的内部成员。
    //类成员的访问限制是通过在类主体内部对各个区域标记 public、private、protected 来指定的。
    //关键字 public、private、protected 称为访问修饰符。
    BoxD box;

    // 不使用成员函数设置长度
    box.length = 10.0; // OK: 因为 length 是公有的
    cout << "Length of box : " << box.length <<endl;

    // 不使用成员函数设置宽度
    // box.width = 10.0; // Error: 因为 width 是私有的
    box.setWidth(10.0);  // 使用成员函数设置宽度
    cout << "Width of box : " << box.getWidth() <<endl;


    SmallBox smallBox;
    smallBox.setSmallHeight(5.0);
    cout << "height of box " << smallBox.getSmallHeight() << endl;
}

class A{
public:
    int a;
    A(){
        a1 = 1;
        a2 = 2;
        a3 = 3;
        a = 4;
    }
    void fun(){
        cout << a << endl;    //正确
        cout << a1 << endl;   //正确
        cout << a2 << endl;   //正确
        cout << a3 << endl;   //正确
    }
public:
    int a1;
protected:
    int a2;
private:
    int a3;
};

//public 继承
class B : public A{
public:
    int a;
    B(int i){
        A();
        a = i;
    }
    void fun(){
        cout << a << endl;       //正确，public成员
        cout << a1 << endl;       //正确，基类的public成员，在派生类中仍是public成员。
        cout << a2 << endl;       //正确，基类的protected成员，在派生类中仍是protected可以被派生类访问。
//        cout << a3 << endl;       //错误，基类的private成员不能被派生类访问。
    }
};

//protected 继承
class C : protected A{
public:
    int a;
    C(int i){
        A();
        a = i;
    }
    void fun(){
        cout << a << endl;       //正确，public成员。
        cout << a1 << endl;       //正确，基类的public成员，在派生类中变成了protected，可以被派生类访问。
        cout << a2 << endl;       //正确，基类的protected成员，在派生类中还是protected，可以被派生类访问。
//        cout << a3 << endl;       //错误，基类的private成员不能被派生类访问。
    }
};

class D : private A{
public:
    int a;
    D(int i){
        A();
        a = i;
    }
    void fun(){
        cout << a << endl;       //正确，public成员。
        cout << a1 << endl;       //正确，基类public成员,在派生类中变成了private,可以被派生类访问。
        cout << a2 << endl;       //正确，基类的protected成员，在派生类中变成了private,可以被派生类访问。
//        cout << a3 << endl;       //错误，基类的private成员不能被派生类访问。
    }
};

//8.2.3.2 访问修饰符继承中的特点
void test8_2_3_2() {
    //有public, protected, private三种继承方式，它们相应地改变了基类成员的访问属性。
    //1.public 继承：基类 public 成员，protected 成员，private 成员的访问属性在派生类中分别变成：public, protected, private
    //2.protected 继承：基类 public 成员，protected 成员，private 成员的访问属性在派生类中分别变成：protected, protected, private
    //3.private 继承：基类 public 成员，protected 成员，private 成员的访问属性在派生类中分别变成：private, private, private
    //但无论哪种继承方式，下面两点都没有改变：
    //1.private 成员只能被本类成员（类内）和友元访问，不能被派生类访问；
    //2.protected 成员可以被派生类访问。
    B b(10);
    cout << b.a << endl;
    cout << b.a1 << endl;   //正确
//    cout << b.a2 << endl;   //错误，类外不能访问protected成员
//    cout << b.a3 << endl;   //错误，类外不能访问private成员
    C c(10);
    cout << c.a << endl;       //正确。public成员
//    cout << c.a1 << endl;      //错误，protected成员不能在类外访问。
//    cout << c.a2 << endl;      //错误，protected成员不能在类外访问。
//    cout << c.a3 << endl;      //错误，private成员不能在类外访问。

    D d(10);
    cout << d.a << endl;       //正确。public成员
//    cout << d.a1 << endl;      //错误，private成员不能在类外访问。
//    cout << d.a2 << endl;      //错误, private成员不能在类外访问。
//    cout << d.a3 << endl;      //错误，private成员不能在类外访问。
}

class Box {
public:
    double length;   // 长度
    double breadth;  // 宽度
    double height;   // 高度
    // 成员函数声明
    double get(void);
    void set(double len, double bre, double hei);
};

void Box::set(double len, double bre, double hei) {
    length = len;
    breadth = bre;
    height = hei;
}

double Box::get(void) {
    return length * breadth * height;
}

//访问数据成员
void test8_3_1_1() {
    Box Box1;        // 声明 Box1，类型为 Box
    // box 1 详述
    Box1.height = 5.0;
    Box1.length = 6.0;
    Box1.breadth = 7.0;
    double volume = 0.0;     // 用于存储体积
    // box 1 的体积
    volume = Box1.height * Box1.length * Box1.breadth;
    cout << "Box1 的体积1：" << volume <<endl;
    Box1.set(16.0, 8.0, 12.0);
    volume = Box1.get();
    cout << "Box1 的体积2：" << volume <<endl;
}

//8.3.1.2 内联成员函数
class BoxYc{
public:
    double length;         // 长度
    double breadth;        // 宽度
    double height;         // 高度
    //成员函数声明
    double getVolume1(void);// 返回体积
    //内联成员函数
    double getVolume2(void){
        return length * breadth * height;
    }
};
// 成员函数定义
double BoxYc::getVolume1(void) {
    return length * breadth * height;
}


void test8_3_1_2() {
    //成员函数可以定义在类定义内部，或者单独使用范围解析运算符 :: 来定义。
    BoxYc Box1;                // 声明 Box1，类型为 Box
    double volume = 0.0;     // 用于存储体积
    Box1.height = 5.0;
    Box1.length = 6.0;
    Box1.breadth = 7.0;
    volume = Box1.getVolume1();
    cout << "调用成员函数，Box1 的体积：" << volume <<endl;
}


//8.3.1.3 内联成员函数
void test8_3_1_3() {
    BoxYc Box1;                // 声明 Box1，类型为 Box
    double volume = 0.0;     // 用于存储体积
    Box1.height = 5.0;
    Box1.length = 6.0;
    Box1.breadth = 7.0;
    volume = Box1.getVolume2();
    cout << "调用内联成员函数，Box1 的体积：" << volume <<endl;
}

//8.3.1.4 友元函数
//https://www.runoob.com/cplusplus/cpp-friend-functions.html
void test8_3_1_4() {

}

//8.3.1.5 静态类成员
//https://www.runoob.com/cplusplus/cpp-static-members.html
void test8_3_1_5() {

}

//8.3.2.1 隐藏的this指针
//https://www.runoob.com/cplusplus/cpp-this-pointer.html
void test8_3_2_1() {

}

//8.3.2.2 指向类的指针
//https://www.runoob.com/cplusplus/cpp-pointer-to-class.html
void test8_3_2_2() {

}





