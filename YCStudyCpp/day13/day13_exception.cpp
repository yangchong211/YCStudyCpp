//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>

using namespace std;

//13.1.1.1 异常的介绍
void test13_1_1_1();
//13.1.1.2 抛出异常
void test13_1_1_2();
//13.1.1.3 捕获异常
void test13_1_1_3();
//13.1.2.1 C++标准的异常
void test13_1_2_1();
//13.1.2.2 定义新的异常
void test13_1_2_2();

// main() 是程序开始执行的地方
int main() {
    test13_1_1_1();
    test13_1_1_2();
    test13_1_1_3();
    test13_1_2_1();
    test13_1_2_2();
    return 0;
}

//13.1.1.1 异常的介绍
void test13_1_1_1() {
    //C++ 异常处理
    //异常是程序在执行期间产生的问题。C++ 异常是指在程序运行时发生的特殊情况，比如尝试除以零的操作。
    //异常提供了一种转移程序控制权的方式。C++ 异常处理涉及到三个关键字：try、catch、throw。

    //throw: 当问题出现时，程序会抛出一个异常。这是通过使用 throw 关键字来完成的。
    //catch: 在您想要处理问题的地方，通过异常处理程序捕获异常。catch 关键字用于捕获异常。
    //try: try 块中的代码标识将被激活的特定异常。它后面通常跟着一个或多个 catch 块。

    //如果有一个块抛出一个异常，捕获异常的方法会使用 try 和 catch 关键字。try 块中放置可能抛出异常的代码，try 块中的代码被称为保护代码。
    //使用 try/catch 语句的语法如下所示：
//    try {
//        // 保护代码
//    } catch (ExceptionName e1) {
//        // catch 块
//    } catch (ExceptionName e2) {
//        // catch 块
//    } catch (ExceptionName eN) {
//        // catch 块
//    }
    //如果 try 块在不同的情境下会抛出不同的异常，这个时候可以尝试罗列多个 catch 语句，用于捕获不同类型的异常。
}


//抛出异常
//您可以使用 throw 语句在代码块中的任何地方抛出异常。throw 语句的操作数可以是任意的表达式，表达式的结果的类型决定了抛出的异常的类型。
//报错日志：
//libc++abi: terminating due to uncaught exception of type char const*
//Abort trap: 6
void test13_1_1_2() {
    int a, b;
//    b = 0;
    if (b == 0) {
        throw "抛出异常";
    }
    int i = a / b;
    std::cout << "test 1 " << i << std::endl;
}

double division(int a, int b) {
    if (b == 0) {
        throw "Division by zero condition!";
    }
    return a / b;
}

//捕获异常
//catch 块跟在 try 块后面，用于捕获异常。您可以指定想要捕捉的异常类型，这是由 catch 关键字后的括号内的异常声明决定的。
void test13_1_1_3() {
    int x = 50;
    int y = 0;
    double z = 0;
    try {
        z = division(x, y);
        cout << "test 2 " << z << "\n" << endl;
    } catch (const char *msg) {
        cerr << "test 2 " << msg << endl;
    }
}

//C++ 提供了一系列标准的异常，定义在 <exception> 中，我们可以在程序中使用这些标准的异常。它们是以父子类层次结构组织起来的，如下所示：
void test13_1_2_1() {
//std::exception	该异常是所有标准 C++ 异常的父类。
//std::bad_alloc	该异常可以通过 new 抛出。
//std::bad_cast	该异常可以通过 dynamic_cast 抛出。
//std::bad_typeid	该异常可以通过 typeid 抛出。
//std::bad_exception	这在处理 C++ 程序中无法预期的异常时非常有用。
//std::logic_error	理论上可以通过读取代码来检测到的异常。
//std::domain_error	当使用了一个无效的数学域时，会抛出该异常。
//std::invalid_argument	当使用了无效的参数时，会抛出该异常。
//std::length_error	当创建了太长的 std::string 时，会抛出该异常。
//std::out_of_range	该异常可以通过方法抛出，例如 std::vector 和 std::bitset<>::operator[]()。
//std::runtime_error	理论上不可以通过读取代码来检测到的异常。
//std::overflow_error	当发生数学上溢时，会抛出该异常。
//std::range_error	当尝试存储超出范围的值时，会抛出该异常。
//std::underflow_error	当发生数学下溢时，会抛出该异常。
}

//异常规格说明
struct MyException : public exception {
    //const throw() 不是函数，这个东西叫异常规格说明，表示 what 函数可以抛出异常的类型，类型说明放到 () 里，
    //这里面没有类型，就是声明这个函数不抛出异常，通常函数不写后面的 throw() 就表示函数可以抛出任何类型的异常。
    const char *what() const throw() {
        return "custom c++ exception";
    }
};

//定义新的异常
//您可以通过继承和重载 exception 类来定义新的异常。下面的实例演示了如何使用 std::exception 类来实现自己的异常：
void test13_1_2_2() {
    try {
        throw MyException();
    }
    catch (MyException &e) {
        std::cout << "MyException caught" << std::endl;
        std::cout << e.what() << std::endl;
    }
    catch (std::exception &e) {
        //其他的错误
    }
}














