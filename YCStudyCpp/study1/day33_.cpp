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


