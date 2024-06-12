//
// Created by 杨充 on 2023/7/4.
//

#include <iostream>
using namespace std;

//15.1.1.1 什么是预处理器
void test15_1_1_1();
//15.1.1.2 #define 预处理
void test15_1_1_2();
//15.1.1.3 参数宏
void test15_1_1_3();
//15.1.1.4 条件编译
void test15_1_1_4();
//15.1.1.5 # 和 ## 运算符
void test15_1_1_5();

//15.1.2.1 什么是预定义宏
void test15_1_2_1();
//15.1.2.2 __cplusplus
void test15_1_2_2();
//15.1.2.3 __FILE__和__LINE__
void test15_1_2_3();
//15.1.2.4 __FUNCTION__
void test15_1_2_4();
//15.1.2.5 __DATE__和__TIME__
void test15_1_2_5();


int main() {
    test15_1_1_1();
    test15_1_1_2();
    test15_1_1_3();
    test15_1_1_4();
    test15_1_1_5();

    test15_1_2_1();
    test15_1_2_2();
    test15_1_2_3();
    test15_1_2_4();
    test15_1_2_5();
    return 0;
}

//15.1.1.1 什么是预处理器
void test15_1_1_1() {
    //C++ 预处理器
    //预处理器是一些指令，指示编译器在实际编译之前所需完成的预处理。
    //所有的预处理器指令都是以井号（#）开头，只有空格字符可以出现在预处理指令之前。预处理指令不是 C++ 语句，所以它们不会以分号（;）结尾。
    //C++ 还支持很多预处理指令，比如 #include、#define、#if、#else、#line 等，让我们一起看看这些重要指令。

}

//#define 预处理
//#define 预处理指令用于创建符号常量。该符号常量通常称为宏，指令的一般形式是：
#define PI 3.14159

//15.1.1.2 #define 预处理
void test15_1_1_2() {
    //在C++中，#define是一个预处理指令，用于定义宏（Macro）。宏是一种在编译时进行文本替换的机制，可以用来定义常量、函数宏、条件编译等。
    //#define指令的一般语法如下：
    //#define identifier replacement
    //其中，identifier是宏的名称，replacement是要替换的文本。
    cout << "15.1.1.2 #define 预处理" << endl;
    cout << "Value of PI :" << PI << endl;
}

//参数宏
//您可以使用 #define 来定义一个带有参数的宏，如下所示：
#define MIN(a, b) (a<b ? a : b)

//15.1.1.3 参数宏
void test15_1_1_3() {
    int i, j;
    i = 200;
    j = 80;
    cout << "MIN 较小的值 : " << MIN(i, j) << endl;
}


//15.1.1.4 条件编译
//条件编译
//有几个指令可以用来有选择地对部分程序源代码进行编译。这个过程被称为条件编译。
void test15_1_1_4() {
    cout << "15.1.1.4 条件编译" << endl;
    int i, j;
    i = 100;
    j = 30;
    //条件预处理器的结构与 if 选择结构很像。请看下面这段预处理器的代码：
#ifdef DEBUG
    cerr <<"Variable x = " << x << endl;
#endif

    //如果在指令 #ifdef DEBUG 之前已经定义了符号常量 DEBUG，则会对程序中的 cerr 语句进行编译。
    //您可以使用 #if 0 语句注释掉程序的一部分，如下所示：
#if 0
    /* 这是注释部分 */
   cout << MKSTR(HELLO C++) << endl;
#endif

    cout <<"The minimum is " << MIN(i, j) << endl;

    //您可以只在调试时进行编译，调试开关可以使用一个宏来实现，如下所示：
#ifdef DEBUG
    cerr <<"Trace: Coming out of main function" << endl;
#endif


    //15.1.1.4 条件编译
    //The minimum is 30
}


//# 和 ## 运算符
//# 和 ## 预处理运算符在 C++ 和 ANSI/ISO C 中都是可用的。
//# 运算符会把 replacement-text 令牌转换为用引号引起来的字符串。
#define MKSTR( x ) #x
//## 运算符用于连接两个令牌。下面是一个实例：
#define concat(a, b) a ## b


//15.1.1.5 # 和 ## 运算符
void test15_1_1_5() {
    cout << "15.1.1.5 # 和 ## 运算符" << endl;
    cout << "# 运算符会把 replacement-text 令牌转换为用引号引起来的字符串 : " << MKSTR(HELLO C++) << endl;
    int xy = 100;
    cout << "## 运算符用于连接两个令牌 : " << concat(x, y) << endl;
    //# 运算符会把 replacement-text 令牌转换为用引号引起来的字符串 : HELLO C++
    //## 运算符用于连接两个令牌 : 100
}

//15.1.2.1 什么是预定义宏
void test15_1_2_1() {
    //在C++中，有一些预定义的宏（Predefined Macros）可以在编译时使用，它们提供了关于编译环境和代码特性的信息。以下是一些常见的预定义宏：
    //__cplusplus：表示当前编译器对C++标准的支持级别。它的值是一个整数，表示C++标准的年份。例如，C++98的值为199711L，C++11的值为201103L，C++14的值为201402L，C++17的值为201703L，C++20的值为202002L。
    //__FILE__：表示当前源文件的文件名（包括路径）。
    //__LINE__：表示当前代码行的行号。
    //__FUNCTION__（或__func__）：表示当前函数的名称。
    //__DATE__：表示当前编译的日期，格式为字符串"MMM DD YYYY"（例如："Jan 01 2022"）。
    //__TIME__：表示当前编译的时间，格式为字符串"HH:MM:SS"（例如："12:34:56"）。
    //__STDC_HOSTED__：表示当前编译器是否支持完整的标准C库（标准C库包括stdio.h、stdlib.h等）。
    //__STDC__：表示当前编译器是否符合ISO C标准。
    //这些预定义宏可以在代码中使用，例如用于调试输出、条件编译等。它们提供了一些有用的信息，可以根据需要在代码中使用。
    //请注意，预定义宏的名称以两个下划线开头和结尾，这是为了避免与用户定义的宏冲突。然而，以两个下划线开头的标识符是保留给编译器和标准库使用的，因此在用户代码中最好避免使用以两个下划线开头的标识符。
}

//15.1.2.2 cplusplus预定义宏
void test15_1_2_2() {
    cout << "15.1.2.2 __cplusplus" << endl;
    std::cout << "C++ Standard: " << __cplusplus << std::endl;
    //C++ Standard: 199711
}

//15.1.2.3 __FILE__和__LINE__
void test15_1_2_3() {
    cout << "15.1.2.3 __FILE__和__LINE__" << endl;
    //使用__FILE__和__LINE__获取当前源文件的文件名和行号：
    std::cout << "File: " << __FILE__ << std::endl;
    std::cout << "Line: " << __LINE__ << std::endl;
    //File: day15_define.cpp
    //Line: 154
}

void foo() {
    std::cout << "Function: " << __FUNCTION__ << std::endl;
}

//15.1.2.4 __FUNCTION__
void test15_1_2_4() {
    cout << "15.1.2.4 __FUNCTION__" << endl;
    //使用__FUNCTION__获取当前函数的名称：
    foo();
}

//15.1.2.5 __DATE__和__TIME__
void test15_1_2_5() {
    cout << "15.1.2.5 __DATE__和__TIME__" << endl;
    std::cout << "Date: " << __DATE__ << std::endl;
    std::cout << "Time: " << __TIME__ << std::endl;
    //Date: Jun 12 2024
    //Time: 17:24:52
}



