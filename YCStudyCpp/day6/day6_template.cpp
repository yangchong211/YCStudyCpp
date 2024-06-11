//
// Created by 杨充 on 2023/7/3.
//

#include <iostream>
#include <string>

using namespace std;

//C++ 模板
//模板是泛型编程的基础，泛型编程即以一种独立于任何特定类型的方式编写代码。
//模板是创建泛型类或函数的蓝图或公式。库容器，比如迭代器和算法，都是泛型编程的例子，它们都使用了模板的概念。


//6.3.1.1 什么是函数模版
void test6_3_1_1();
//6.3.1.2 函数模板运用
void test6_3_1_2();

int main() {
    test6_3_1_1();
    test6_3_1_2();
}

template <typename T>
void functionName(T parameter) {
    // 函数体
    //在上述示例中，template <typename T>是函数模板的声明部分，T是一个类型参数，可以在函数体中使用。
    //functionName是函数模板的名称，parameter是函数的参数，类型为T。
    //通过使用函数模板，我们可以编写一次通用的函数定义，然后根据实际需要的数据类型进行实例化。编译器会根据实际使用的数据类型生成对应的函数代码。
}
template <typename T>
T sum(T a, T b) {
    //定义了一个函数模板sum()，它接受两个参数并返回它们的和。通过使用typename T作为类型参数，我们可以在函数体中使用通用的类型T。
    return a + b;
}


//6.3.1.1 什么是函数模版
void test6_3_1_1() {
    //模板是泛型编程的基础，泛型编程即以一种独立于任何特定类型的方式编写代码。
    //模板是创建泛型类或函数的蓝图或公式。库容器，比如迭代器和算法，都是泛型编程的例子，它们都使用了模板的概念。
    //每个容器都有一个单一的定义，比如 向量，我们可以定义许多不同类型的向量，比如 vector <int> 或 vector <string>。

    //函数模板是C++中的一种特性，它允许我们编写通用的函数，可以用于多种数据类型而不需要为每种数据类型编写不同的函数。
    //函数模板通过参数化类型来实现通用性，可以在编译时根据实际使用的数据类型生成具体的函数。

    int x = 5, y = 10;
    std::cout << "Sum of integers: " << sum(x, y) << std::endl;
    double a = 3.14, b = 2.71;
    std::cout << "Sum of doubles: " << sum(a, b) << std::endl;
    //分别使用整数和浮点数调用sum()函数，并输出计算结果。编译器会根据实际使用的数据类型生成对应的函数代码，从而实现通用的求和功能。
    //函数模板是C++中强大的特性，它提供了一种通用的编程方式，可以减少代码的重复性，提高代码的可重用性和灵活性。
}



template <typename T>
inline T const& Max(T const& a , T const& b){
    return a < b ? b : a;
}

int MaxInt(int a , int b){
    return a < b ? b : a;
}

//6.3.1.2 函数模板运用
void test6_3_1_2() {
    int i= 39;
    int j= 20;
    cout << "Max(i , j) " << Max(i , j) << endl;
    cout << "MaxInt(i , j) " << MaxInt(i , j) << endl;

    double f1 = 13.5;
    double f2 = 20.7;
    cout << "Max(f1, f2): " << Max(f1, f2) << endl;
}