//
// Created by 杨充 on 2023/7/4.
//


//C++ 预处理器
//预处理器是一些指令，指示编译器在实际编译之前所需完成的预处理。
//所有的预处理器指令都是以井号（#）开头，只有空格字符可以出现在预处理指令之前。预处理指令不是 C++ 语句，所以它们不会以分号（;）结尾。
//C++ 还支持很多预处理指令，比如 #include、#define、#if、#else、#line 等，让我们一起看看这些重要指令。

#include <iostream>

using namespace std;

//#define 预处理
//#define 预处理指令用于创建符号常量。该符号常量通常称为宏，指令的一般形式是：
#define PI 3.14159

//参数宏
//您可以使用 #define 来定义一个带有参数的宏，如下所示：
#define MIN(a, b) (a<b ? a : b)

//条件编译
//有几个指令可以用来有选择地对部分程序源代码进行编译。这个过程被称为条件编译。
//条件预处理器的结构与 if 选择结构很像。
#ifdef DEBUG
cerr <<"Variable x = " << x << endl;
#endif

//# 和 ## 运算符
//# 和 ## 预处理运算符在 C++ 和 ANSI/ISO C 中都是可用的。
//# 运算符会把 replacement-text 令牌转换为用引号引起来的字符串。
#define MKSTR( x ) #x
//## 运算符用于连接两个令牌。下面是一个实例：
#define concat(a, b) a ## b



void test1();
void test2();
void test3();
void test4();

int main() {
    test1();
    test2();
    test3();
    test4();
    return 0;
}

void test1() {
    cout << "Value of PI :" << PI << endl;
}

void test2() {
    int i, j;
    i = 200;
    j = 80;
    cout << "MIN 较小的值 : " << MIN(i, j) << endl;
}

void test3() {
    int i, j;
    i = 100;
    j = 30;

    //您可以只在调试时进行编译，调试开关可以使用一个宏来实现，如下所示：
    //如果在指令 #ifdef DEBUG 之前已经定义了符号常量 DEBUG，则会对程序中的 cerr 语句进行编译。
#ifdef DEBUG
    cerr <<"Trace: Inside main function" << endl;
#endif


    //可以使用 #if 0 语句注释掉程序的一部分，如下所示：
#if 0
    /* 这是注释部分 */
    //不进行编译的代码
   cout << MKSTR(HELLO C++) << endl;
#endif
    cout << "The minimum is " << MIN(i, j) << endl;


#ifdef DEBUG
    cerr <<"Trace: Coming out of main function" << endl;
#endif
}


void test4() {
    cout << "# 运算符会把 replacement-text 令牌转换为用引号引起来的字符串 : " << MKSTR(HELLO C++) << endl;
    int xy = 100;
    cout << "## 运算符用于连接两个令牌 : " << concat(x, y) << endl;
}




