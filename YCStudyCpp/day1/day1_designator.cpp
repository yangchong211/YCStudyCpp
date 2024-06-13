//
// Created by 杨充 on 2024/6/13.
// 1.2 修饰符和标识符
//
#include <iostream>

using namespace std;

//1.2.2.1 什么是标志符
void test1_2_2_1();
//1.2.2.2 有效标志符
void test1_2_2_2();
//1.2.2.3 无效标志符
void test1_2_2_3();

int main() {
    test1_2_2_1();
    test1_2_2_2();
    test1_2_2_3();
    return 0;
}

//1.2.2.1 什么是标志符
void test1_2_2_1() {
    //C++ 标识符是用来标识变量、函数、类、模块，或任何其他用户自定义项目的名称。
}

//1.2.2.2 有效标志符
void test1_2_2_2() {
    //一个标识符以字母 A-Z 或 a-z 或下划线 _ 开始，后跟零个或多个字母、下划线和数字（0-9）。
    //下面列出几个有效的标识符：
    //
    //mohd       zara    abc   move_name  a_123
    //myname50   _temp   j     a23b9      retVal
}

//1.2.2.3 无效标志符
void test1_2_2_3() {
    //C++ 标识符内不允许出现标点字符，比如 @、& 和 %。C++ 是区分大小写的编程语言。
}


