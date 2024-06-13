//
// Created by 杨充 on 2024/6/13.
//

#include <iostream>
#include <memory>
using namespace std;

//12.2.1.1 std::shared_ptr
void test12_2_1_1();
//12.2.1.2 std::unique_ptr
void test12_2_1_2();

int main() {
    test12_2_1_1();
    test12_2_1_2();
    return 0;
}

class MyClass {
public:
    MyClass() {
        std::cout << "Constructor called" << std::endl;
    }
    //析构函数会在对象被销毁时自动调用，用于清理对象所占用的资源。
    ~MyClass() {
        std::cout << "Destructor called" << std::endl;
    }
    void eat() {
        cout << "eat called" << endl;
    }
    void sleep() {
        cout << "sleep called" << endl;
    }
};


//12.2.1.1 std::shared_ptr
void test12_2_1_1() {
    //std::shared_ptr是C++11中引入的智能指针类，用于管理动态分配的内存。它提供了自动的内存管理和资源释放，可以避免手动使用new和delete运算符。
    //使用std::shared_ptr可以共享指针的所有权，多个std::shared_ptr可以指向同一个对象，并且会自动跟踪对象的引用计数。
    //只有当最后一个std::shared_ptr超出作用域或被显式释放时，才会自动释放对象的内存。

    std::cout << "12.2.1.1 std::shared_ptr" << std::endl;
    //创建了一个std::shared_ptr对象ptr1，并通过new运算符分配了一个MyClass对象。
    std::shared_ptr<MyClass> ptr1(new MyClass()); // 创建一个std::shared_ptr对象
    {
        std::shared_ptr<MyClass> ptr2 = ptr1; // 共享所有权
        std::shared_ptr<MyClass> ptr3(ptr1); // 共享所有权

        // 使用ptr1、ptr2和ptr3
        // ...
        ptr1->eat();
        ptr2->eat();
        ptr3->eat();
    }
    // ptr2和ptr3超出作用域，但对象不会被立即销毁

    // 使用ptr1
    ptr1->sleep();
    // ...

    //Constructor called
    //eat called
    //eat called
    //eat called
    //sleep called
    //Destructor called
    //std::shared_ptr还提供了其他功能，如自定义删除器、使用自定义分配器、获取指向对象的原始指针等。它是一种方便且安全的内存管理工具，可以减少内存泄漏和悬挂指针的风险。
}


//12.2.1.2 std::unique_ptr
void test12_2_1_2() {
    //std::unique_ptr是C++11中引入的智能指针类，用于管理动态分配的内存。与std::shared_ptr不同，std::unique_ptr不允许多个指针共享同一个对象的所有权，它是独占所有权的智能指针。
    //使用std::unique_ptr可以确保只有一个指针拥有对象的所有权，当该指针超出作用域或被显式释放时，对象的内存会被自动释放。

    std::cout << "12.2.1.2 std::unique_ptr" << std::endl;
    //首先包含了<memory>头文件以使用std::unique_ptr。然后，创建了一个std::unique_ptr对象ptr1，并通过new运算符分配了一个MyClass对象。
    std::unique_ptr<MyClass> ptr1(new MyClass());
    {
        //由于std::unique_ptr的独占性质，不能将一个std::unique_ptr赋值给另一个std::unique_ptr或进行拷贝构造。
        // std::unique_ptr不允许共享所有权，以下代码会导致编译错误
         //std::unique_ptr<MyClass> ptr2 = ptr1;
        // std::unique_ptr<MyClass> ptr3(ptr1);

        // 使用ptr1
        ptr1->eat();
        // ...
    }
    // ptr1超出作用域，对象被销毁
    ptr1->sleep();
    //std::unique_ptr还提供了其他功能，如自定义删除器、使用自定义分配器、获取指向对象的原始指针等。它是一种轻量级的智能指针，适用于需要独占所有权的场景。
}




