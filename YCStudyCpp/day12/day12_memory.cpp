//
// Created by 杨充 on 2023/6/20.
//


//了解动态内存在 C++ 中是如何工作的是成为一名合格的 C++ 程序员必不可少的。C++ 程序中的内存分为两个部分：
//栈：在函数内部声明的所有变量都将占用栈内存。
//堆：这是程序中未使用的内存，在程序运行时可用于动态分配内存。

#include <iostream>
using namespace std;

//12.1.1.1 什么是动态内存
void test12_1_1_1();
//12.1.1.2 new 和 delete 运算符
void test12_1_1_2();
//12.1.1.3 new 和 malloc区别
void test12_1_1_3();
//12.1.1.4 对象的动态内存分配
void test12_1_1_4();

//12.1.2.1 一维数组的动态内存分配
void test12_1_2_1();
//12.1.2.2 二维数组的动态内存分配
void test12_1_2_2();


int main() {
    test12_1_1_1();
    test12_1_1_2();
    test12_1_1_3();
    test12_1_1_4();

    test12_1_2_1();
    test12_1_2_2();
    return 0;
}

//12.1.1.1 什么是动态内存
void test12_1_1_1() {
    //了解动态内存在 C++ 中是如何工作的是成为一名合格的 C++ 程序员必不可少的。C++ 程序中的内存分为两个部分：
    //
    //栈：在函数内部声明的所有变量都将占用栈内存。
    //堆：这是程序中未使用的内存，在程序运行时可用于动态分配内存。
    //很多时候，您无法提前预知需要多少内存来存储某个定义变量中的特定信息，所需内存的大小需要在运行时才能确定。
    //
    //在 C++ 中，您可以使用特殊的运算符为给定类型的变量在运行时分配堆内的内存，这会返回所分配的空间地址。这种运算符即 new 运算符。
    //
    //如果您不再需要动态分配的内存空间，可以使用 delete 运算符，删除之前由 new 运算符分配的内存。
}


//12.1.1.2 new 和 delete 运算符
void test12_1_1_2() {
    //需要注意的是，使用new和delete运算符时要小心，确保在适当的时候释放内存，以避免内存泄漏和悬挂指针等问题。
    //另外，C++11引入了智能指针（如std::shared_ptr和std::unique_ptr）等更安全和方便的内存管理方式，它们可以自动管理内存的分配和释放，避免手动使用new和delete运算符。
    std::cout << "12.1.1.2 new 和 delete 运算符" << std::endl;
    double *ptr = NULL; // 初始化为 null 的指针
    //分配单个对象：这将在堆上分配一个type类型的对象，并返回指向该对象的指针。例如，分配一个double类型的对象：
    //在这种情况下，ptr是指向分配的int对象的指针。
    ptr = new double;   // 为变量请求内存
    if( !(ptr  = new double )) {
        cout << "Error: out of memory." << endl;
    }

    *ptr = 29494.99;     // 在分配的地址存储值
    cout << "Value of pvalue : " << *ptr << endl;
    delete ptr;         // 释放内存


    //释放内存2
    string *str = NULL;
    str = new string;
    cout << "Value of str : " << *str << " --  " << str << endl;
    //str = "yangchong";
    *str = "yangchong";
    cout << "Value of str : " << *str << endl;
    //删除之前的对象
    delete str;


    //分配数组：这将在堆上分配一个大小为size的type类型的数组，并返回指向数组的第一个元素的指针。例如，分配一个包含5个int元素的数组：
    int* arr = new int[5];
    //释放数组
    delete[] arr;

    //Value of pvalue : 29495
    //Value of str :  --  0x600002031280
    //Value of str : yangchong
}


//12.1.1.3 new 和 malloc区别
void test12_1_1_3() {
    //malloc() 函数在 C 语言中就出现了，在 C++ 中仍然存在，但建议尽量不要使用 malloc() 函数。
    //new 与 malloc() 函数相比，其主要的优点是，new 不只是分配了内存，它还创建了对象。

    //在C++中，new和malloc都可以用于动态分配内存，但它们之间有一些重要的区别：
    //类型安全性：new是C++的运算符，它会根据所需的类型进行内存分配，并返回指向正确类型的指针。这意味着在使用new分配内存时，不需要进行显式的类型转换。而malloc是C语言的函数，它返回void*类型的指针，需要进行显式的类型转换。
    //构造函数调用：new运算符在分配内存后会自动调用对象的构造函数，以初始化对象的状态。这对于C++中的类对象非常重要。而malloc只是分配内存块，并不会调用构造函数，因此需要手动调用构造函数来初始化对象。
    //内存大小：new运算符根据所需类型的大小进行内存分配，而malloc函数需要显式指定要分配的内存块的大小。
    //异常处理：new运算符在内存分配失败时会抛出std::bad_alloc异常，可以通过异常处理机制来处理。而malloc函数在内存分配失败时返回NULL指针，需要手动检查返回值来处理分配失败的情况。
    //释放内存：使用new运算符分配的内存应该使用delete运算符进行释放，而使用malloc函数分配的内存应该使用free函数进行释放。


    //综上所述，new运算符在C++中更常用，因为它提供了更高的类型安全性、自动调用构造函数和异常处理等特性。而malloc函数在C++中仍然可以使用，特别是在与C代码进行交互时。
}

class MyClass {
    //首先定义了一个名为MyClass的类，其中包含构造函数和析构函数。
public:
    MyClass() {
        cout << "Constructor called" << endl;
    }
    //同时，析构函数会在对象被销毁时自动调用，用于清理对象所占用的资源。
    ~MyClass() {
        cout << "Destructor called" << endl;
    }
    void eat() {
        cout << "eat called" << endl;
    }
};

//12.1.1.4 对象的动态内存分配
void test12_1_1_4() {
    std::cout << "12.1.1.4 对象的动态内存分配" << std::endl;
    MyClass* obj = new MyClass(); // 动态分配对象
    // 使用动态分配的对象
    obj->eat();
    // ...
    delete obj; // 释放动态分配的对象内存

    //Constructor called
    //eat called
    //Destructor called
}

//12.1.2.1 一维数组的动态内存分配
void test12_1_2_1() {
    std::cout << "12.1.2.1 一维数组的动态内存分配" << std::endl;
    int size = 5; // 数组大小
    //使用new运算符动态分配了一个大小为size的int类型数组，并将返回的指针赋值给array。
    int* array = new int[size]; // 动态分配一维数组
    // 使用动态分配的数组
    for (int i = 0; i < size; i++) {
        //可以使用动态分配的数组进行操作，例如给数组元素赋值和打印数组元素。
        array[i] = i + 1;
        cout << array[i] << " " << endl;
    }
    //使用delete[]运算符释放动态分配的数组内存。
    delete[] array;
    //需要注意的是，使用动态内存分配后，必须在不再需要时使用delete[]运算符释放内存，以避免内存泄漏。
}

//12.1.2.2 二维数组的动态内存分配
void test12_1_2_2() {
    std::cout << "12.1.2.2 二维数组的动态内存分配" << std::endl;
    int **p;
    int i, j;
    //开始分配4行8列的二维数据
    p = new int *[4];
    for (int i = 0; i < 4; ++i) {
        p[i] = new int[8];
    }
    for (i = 0; i < 4; i++) {
        for (j = 0; j < 8; j++) {
            p[i][j] = j * i;
        }
    }
    //打印数据
    for (i = 0; i < 4; i++) {
        for (j = 0; j < 8; j++) {
            if (j == 0) {
                cout << endl;
            }
            cout << "二维数组" << p[i][j] << "\t" << endl;
        }
    }
    //开始释放申请的堆
    for (i = 0; i < 4; i++) {
        delete[] p[i];
    }
    delete[] p;
}








