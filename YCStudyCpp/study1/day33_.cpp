//
// Created by 杨充 on 2024/4/2.
//
#include <iostream>
using namespace std;


//算术运算符
void test1() {
    int a = 21;
    int b = 10;
    int c;

    c = a + b;
    cout << "Line 1 - c 的值是 " << c << endl;
    c = a - b;
    cout << "Line 2 - c 的值是 " << c << endl;
    c = a * b;
    cout << "Line 3 - c 的值是 " << c << endl;
    c = a / b;  //分子除以分母
    cout << "Line 4 - c 的值是 " << c << endl;
    c = a % b;  //取模运算符，整除后的余数
    cout << "Line 5 - c 的值是 " << c << endl;

    int d = 10;   //  测试自增、自减
    c = d++;
    cout << "Line 6 - c 的值是 " << c << endl;

    d = 10;    // 重新赋值
    c = d--;
    cout << "Line 7 - c 的值是 " << c << endl << "\n";
    //Line 1 - c 的值是 31
    //Line 2 - c 的值是 11
    //Line 3 - c 的值是 210
    //Line 4 - c 的值是 2
    //Line 5 - c 的值是 1
    //Line 6 - c 的值是 10
    //Line 7 - c 的值是 10
}

//关系运算符
void test2() {
    int a = 21;
    int b = 10;
    int c;
    if (a == b) {
        cout << "Line 1 - a 等于 b" << endl;
    } else {
        cout << "Line 1 - a 不等于 b" << endl;
    }
    if (a < b) {
        cout << "Line 2 - a 小于 b" << endl;
    } else {
        cout << "Line 2 - a 不小于 b" << endl;
    }
    if (a > b) {
        cout << "Line 3 - a 大于 b" << endl;
    } else {
        cout << "Line 3 - a 不大于 b" << endl;
    }
    /* 改变 a 和 b 的值 */
    a = 5;
    b = 20;
    if (a <= b) {
        cout << "Line 4 - a 小于或等于 b" << endl;
    }
    if (b >= a) {
        cout << "Line 5 - b 大于或等于 a" << endl;
    }
    cout << "\n";
}

//逻辑运算符
void test3() {
    int a = 5;
    int b = 20;
    int c;

    if (a && b) {
        cout << "Line 1 - 条件为真" << endl;
    }
    if (a || b) {
        cout << "Line 2 - 条件为真" << endl;
    }
    /* 改变 a 和 b 的值 */
    a = 0;
    b = 10;
    if (a && b) {
        cout << "Line 3 - 条件为真" << endl;
    } else {
        cout << "Line 4 - 条件不为真" << endl;
    }
    if (!(a && b)) {
        cout << "Line 5 - 条件为真" << endl;
    }
    cout << "\n";
}

//位运算符
//假设如果 A = 60，且 B = 13，现在以二进制格式表示，它们如下所示：
//A = 0011 1100
//B = 0000 1101
//-----------------
//A&B = 0000 1100
//A|B = 0011 1101
//A^B = 0011 0001
//~A  = 1100 0011
void test4(){
    unsigned int a = 60;      // 60 = 0011 1100
    unsigned int b = 13;      // 13 = 0000 1101
    int c = 0;
    c = a & b;             // 12 = 0000 1100
    cout << "Line 1 - c 的值是 " << c << endl ;
    c = a | b;             // 61 = 0011 1101
    cout << "Line 2 - c 的值是 " << c << endl ;
    c = a ^ b;             // 49 = 0011 0001
    cout << "Line 3 - c 的值是 " << c << endl ;
    c = ~a;                // -61 = 1100 0011
    cout << "Line 4 - c 的值是 " << c << endl ;
    c = a << 2;            // 240 = 1111 0000
    cout << "Line 5 - c 的值是 " << c << endl ;
    c = a >> 2;            // 15 = 0000 1111
    cout << "Line 6 - c 的值是 " << c << endl ;
    cout << "\n";
    //Line 1 - c 的值是 12
    //Line 2 - c 的值是 61
    //Line 3 - c 的值是 49
    //Line 4 - c 的值是 -61
    //Line 5 - c 的值是 240
    //Line 6 - c 的值是 15
}



//局部变量
//在函数或一个代码块内部声明的变量，称为局部变量。它们只能被函数内部或者代码块内部的语句使用。下面的实例使用了局部变量：
void test1() {
    int b;
    int c, a = 10;
    b = 20;
    c = a + b;
    cout << c << " \n";
}

//全局变量
//在所有函数外部定义的变量（通常是在程序的头部），称为全局变量。全局变量的值在程序的整个生命周期内都是有效的。
//全局变量可以被任何函数访问。也就是说，全局变量一旦声明，在整个程序中都是可用的。下面的实例使用了全局变量和局部变量：
// 全局变量声明
int g;

void test2() {
    // 局部变量声明
    int a, b;
    // 实际初始化
    a = 10;
    b = 20;
    g = a + b;
    cout << g << " \n";
}

//在程序中，局部变量和全局变量的名称可以相同，但是在函数内，局部变量的值会覆盖全局变量的值。下面是一个实例：
void test3() {
    // 局部变量声明
    int g = 10;
    cout << g << " \n";
}

//类作用域
//类作用域指的是在类内部声明的变量：
class MyClass {
public:
    //类作用域变量
    static int class_var;
private:
    int i;
};


//以上实例中，MyClass 类中声明了一个名为 class_var 的类作用域变量。可以使用类名和作用域解析运算符 :: 来访问这个变量。
//在 test4() 函数中访问 class_var 时输出的是 30。
int MyClass::class_var = 30;
void test4() {
//    int MyClass::class_var = 20;
    std::cout << "类变量" << MyClass::class_var << std::endl;
}



/*
 * 这个程序演示了有符号整数和无符号整数之间的差别
 *
*/
//C++ 修饰符类型
//C++ 允许在 char、int 和 double 数据类型前放置修饰符。
//
//修饰符是用于改变变量类型的行为的关键字，它更能满足各种情境的需求。
//
//下面列出了数据类型修饰符：
//
//signed：表示变量可以存储负数。对于整型变量来说，signed 可以省略，因为整型变量默认为有符号类型。
//
//unsigned：表示变量不能存储负数。对于整型变量来说，unsigned 可以将变量范围扩大一倍。
//
//short：表示变量的范围比 int 更小。short int 可以缩写为 short。
//
//long：表示变量的范围比 int 更大。long int 可以缩写为 long。
//
//long long：表示变量的范围比 long 更大。C++11 中新增的数据类型修饰符。
//
//float：表示单精度浮点数。
//
//double：表示双精度浮点数。
//
//bool：表示布尔类型，只有 true 和 false 两个值。
//
//char：表示字符类型。
//
//wchar_t：表示宽字符类型，可以存储 Unicode 字符。


void test1(){
    short int i;           // 有符号短整数
    short unsigned int j;  // 无符号短整数
    j = 50000;
    i = j;
    cout << i << " " << j << "\n";
    //-15536 50000
    //上述结果中，无符号短整数 50,000 的位模式被解释为有符号短整数 -15,536。
}

//C++ 中的类型限定符
//类型限定符提供了变量的额外信息，用于在定义变量或函数时改变它们的默认行为的关键字。
//const	const 定义常量，表示该变量的值不能被修改。。
//volatile	修饰符 volatile 告诉该变量的值可能会被程序以外的因素改变，如硬件或其他线程。。
//restrict	由 restrict 修饰的指针是唯一一种访问它所指向的对象的方式。只有 C99 增加了新的类型限定符 restrict。
//mutable	表示类中的成员变量可以在 const 成员函数中被修改。
//static	用于定义静态变量，表示该变量的作用域仅限于当前文件或当前函数内，不会被其他文件或函数访问。
//register	用于定义寄存器变量，表示该变量被频繁使用，可以存储在CPU的寄存器中，以提高程序的运行效率。
void test2(){
    //const 实例
    const int NUM = 10; // 定义常量 NUM，其值不可修改
    const int* ptr = &NUM; // 定义指向常量的指针，指针所指的值不可修改
    int const* ptr2 = &NUM; // 和上面一行等价

    //volatile 实例
    volatile int num = 20; // 定义变量 num，其值可能会在未知的时间被改变

    //mutable 实例
    class Example {
    public:
        int get_value() const {
            return value_; // const 关键字表示该成员函数不会修改对象中的数据成员
        }
        void set_value(int value) const {
            value_ = value; // mutable 关键字允许在 const 成员函数中修改成员变量
        }
    private:
        mutable int value_;
    };

    //static 实例
    static int count = 0; // static 关键字使变量 count 存储在程序生命周期内都存在
    count++;
}

//register 实例
//void example_function(register int num) {
// register 关键字建议编译器将变量 num 存储在寄存器中
// 以提高程序执行速度
// 但是实际上是否会存储在寄存器中由编译器决定
//}

//auto 存储类
//register 存储类
void test3() {
    //自 C++ 11 以来，auto 关键字用于两种情况：声明变量时根据初始化表达式自动推断该变量的类型、声明函数时函数返回值的占位符。
    auto f=3.14;      //double
    auto s("hello");  //const char*
    auto z = new auto(9); // int*
//    auto x1 = 5, x2 = 5.0, x3='r';//错误，必须是初始化为同一类型

    //register 存储类
    //register 存储类用于定义存储在寄存器中而不是 RAM 中的局部变量。这意味着变量的最大尺寸等于寄存器的大小（通常是一个词），且不能对它应用一元的 '&' 运算符（因为它没有内存位置）。
//    {
//        register int  miles;
//    }
}



void test4() {

}



