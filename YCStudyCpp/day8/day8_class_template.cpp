//
// Created by 杨充 on 2024/6/12.
//
#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <string>
#include <stdexcept>

using namespace std;

//8.2.4.1 什么是类模版
void test8_2_4_1();
//8.2.4.2 类模版设计和实践
void test8_2_4_2();

int main() {
    test8_2_4_1();
    test8_2_4_2();
    return 0;
}

template <typename T> class MyTemplateClass {
private:
    T data;
public:
    MyTemplateClass(T value) {
        data = value;
    }
    T getData() {
        return data;
    }
};

//8.2.4.1 什么是类模版
void test8_2_4_1() {
    //在C++中，类模板（Class Template）是一种用于创建通用类的机制。
    //类模板允许我们定义一个通用的类模板，其中的某些成员可以使用泛型类型，从而使得类可以适用于多种不同的数据类型。

    cout << "8.2.4.1 什么是类模版" << endl;
    //类模板的定义使用关键字template，后面跟着模板参数列表。模板参数可以是类型参数、非类型参数或模板参数。
    // 类型参数用于指定泛型类型，非类型参数用于指定常量值，而模板参数用于指定其他模板。

    //在上述示例中，我们定义了一个名为MyTemplateClass的类模板。模板参数列表中的typename T表示类型参数，它表示一个泛型类型。在类模板中，我们可以使用T作为类型的占位符。
    //在类模板中，我们可以使用T作为成员变量的类型、函数参数的类型以及函数返回值的类型。在构造函数和getData()函数中，我们使用了T作为数据类型。
    //通过使用类模板，我们可以在实例化时指定具体的类型，从而创建特定类型的对象。例如：
    MyTemplateClass<int> obj1(10);  // 实例化一个MyTemplateClass对象，其中T被替换为int
    int value1 = obj1.getData();     // 获取对象中的数据
    cout << "T被替换为int，获取对象中的数据 " << value1 << endl;


    MyTemplateClass<double> obj2(3.14);  // 实例化一个MyTemplateClass对象，其中T被替换为double
    double value2 = obj2.getData();      // 获取对象中的数据
    cout << "T被替换为double，获取对象中的数据 " << value2 << endl;

    //T被替换为int，获取对象中的数据 10
    //T被替换为double，获取对象中的数据 3.14

    //通过实例化类模板，我们可以创建适用于不同类型的对象，而无需为每种类型编写单独的类。这使得代码更加灵活和可重用，同时提高了代码的效率和可维护性。
}

template <class T> class MyStack {
private:
    vector<T> elems;     // 元素
public:
    void push(T const&);  // 入栈
    void pop();               // 出栈
    T top() const;            // 返回栈顶元素
    bool empty() const{       // 如果为空则返回真。
        return elems.empty();
    }
};

template<class T> void MyStack<T>::push(const T & elem) {
    // 追加传入元素的副本
    elems.push_back(elem);
}

template<class T> void MyStack<T>::pop() {
    if (elems.empty()) {
        throw out_of_range("MyStack<>::pop(): empty stack");
    }
    // 删除最后一个元素
    elems.pop_back();
}

template<class T> T MyStack<T>::top() const {
    if (elems.empty()) {
        throw out_of_range("MyStack<>::top(): empty stack");
    }
    // 返回最后一个元素的副本
    return elems.back();
}



//8.2.4.2 类模版设计和实践
void test8_2_4_2() {
    cout << "8.2.4.2 类模版设计和实践" << endl;
    try {
        MyStack<int>         intStack;  // int 类型的栈
        MyStack<string> stringStack;    // string 类型的栈

        // 操作 int 类型的栈
        intStack.push(7);
        cout << "T被替换为int，获取栈顶数据 " <<  intStack.top() <<endl;

        // 操作 string 类型的栈
        stringStack.push("hello");
        cout << "T被替换为string，获取栈顶数据 " <<  stringStack.top() <<endl;
        stringStack.pop();
        stringStack.pop();
    } catch (exception & ex) {
        cerr << "Exception: " << ex.what() <<endl;
    }
}


