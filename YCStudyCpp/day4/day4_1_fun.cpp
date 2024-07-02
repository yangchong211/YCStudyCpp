//
// Created by 杨充 on 2024/6/27.
//
#include <iostream>
#include <string>

using namespace std;



//4.1.1.1 函数的定义
void test4_1_1_1();
//4.1.1.2 无参函数
void test4_1_1_2();
//4.1.1.3 有参函数
void test4_1_1_3();
//4.1.1.4 空函数
void test4_1_1_4();


//4.1.2.1 内部函数
void test4_1_2_1();
//4.1.2.2 外部函数
void test4_1_2_2();


int main() {
    test4_1_1_1();
    test4_1_1_2();
    test4_1_1_3();
    test4_1_1_4();

    test4_1_2_1();
    test4_1_2_2();
    return 0;
}

//4.1.1.1 函数的定义
void test4_1_1_1() {
    //return_type function_name( parameter list )
    //{
    //   body of the function
    //}

    //在 C++ 中，函数由一个函数头和一个函数主体组成。下面列出一个函数的所有组成部分：
    //返回类型：一个函数可以返回一个值。return_type 是函数返回的值的数据类型。有些函数执行所需的操作而不返回值，在这种情况下，return_type 是关键字 void。
    //函数名称：这是函数的实际名称。函数名和参数列表一起构成了函数签名。
    //参数：参数就像是占位符。当函数被调用时，您向参数传递一个值，这个值被称为实际参数。参数列表包括函数参数的类型、顺序、数量。参数是可选的，也就是说，函数可能不包含参数。
    //函数主体：函数主体包含一组定义函数执行任务的语句。
}

// 无参函数示例
void say_hello1() {
    std::cout << "Hello, World!" << std::endl;
}

//4.1.1.2 无参函数
void test4_1_1_2() {
    cout << "4.1.1.2 无参函数" << endl;
    say_hello1();
    //无参函数在C++中非常常见，用于执行不需要输入参数的操作。可以根据具体的需求和功能来定义和使用无参函数。
}

// 有参函数示例
void greet(const std::string & name) {
    std::cout << "Hello, " << name << "!" << std::endl;
}

//4.1.1.3 有参函数
void test4_1_1_3() {
    cout << "4.1.1.3 有参函数" << endl;
    //greet()是一个有参函数，它接受一个std::string类型的参数name。
    greet("杨充");
    greet("逗比");
    //有参函数在C++中非常常见，用于接受输入参数并执行相应的操作。
    //参数可以是基本数据类型（如整数、浮点数等）、自定义类型（如结构体、类等）或引用类型。
}


// 空函数示例
void doNothing() {
    // 什么也不做
}

//4.1.1.4 空函数
void test4_1_1_4() {
    cout << "4.1.1.4 空函数" << endl;
    // 调用空函数
    doNothing();
    //doNothing()是一个空函数，它没有任何操作或返回值。在函数中，我们调用了doNothing()函数，但它不会执行任何实际的操作。
    //空函数在某些情况下可能会有用，例如在程序中占位或作为占位符函数。它可以用作函数原型的占位符，以后可以在需要时填充具体的实现。
    //需要注意的是，空函数并不是一种常见的编程实践，因为它没有实际的功能。在实际开发中，应该根据需求和功能来定义和实现有意义的函数。
}

//void outerFunction() {
//    // 内部函数
//    void innerFunction() {
//        std::cout << "This is an inner function." << std::endl;
//    }
//
//    // 调用内部函数
//    innerFunction();
//}

//4.1.2.1 内部函数
void test4_1_2_1() {
//    outerFunction();
    //innerFunction()是在outerFunction()内部定义和实现的内部函数。
    //在outerFunction()中，我们调用了innerFunction()，它会输出"This is an inner function."。

    //内部函数可以访问外部函数的局部变量和参数，但外部函数无法直接调用内部函数。内部函数的作用域仅限于外部函数内部，它们对外部世界是不可见的。

    //内部函数在某些情况下可以用于封装和隐藏代码逻辑，提高代码的可读性和模块化。然而，内部函数的使用应该谨慎，避免过度复杂化代码结构。
}


// 外部函数
void externalFunction() {
    std::cout << "This is an external function." << std::endl;
}

//4.1.2.2 外部函数
void test4_1_2_2() {
    // 调用外部函数
    externalFunction();
}



