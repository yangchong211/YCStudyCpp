//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
using namespace std;

//7.1.1.1 输入输出介绍
void test7_1_1_1();
//7.1.1.2 I/O 库头文件
void test7_1_1_2();
//7.1.1.3 标准输出流（cout）
void test7_1_1_3();
//7.1.1.4 标准输入流（cin）
void test7_1_1_4();
//7.1.1.5 标准错误流（cerr）
void test7_1_1_5();
//7.1.1.6 标准日志流（clog）
void test7_1_1_6();



int main() {
    test7_1_1_1();
    test7_1_1_2();
    test7_1_1_3();
    test7_1_1_4();
    test7_1_1_5();
    test7_1_1_6();
    return 0;
}


//7.1.1.1 输入输出介绍
void test7_1_1_1() {
    //C++ 基本的输入输出
    //C++ 标准库提供了一组丰富的输入/输出功能，我们将在后续的章节进行介绍。本章将讨论 C++ 编程中最基本和最常见的 I/O 操作。
    //C++ 的 I/O 发生在流中，流是字节序列。如果字节流是从设备（如键盘、磁盘驱动器、网络连接等）流向内存，这叫做输入操作。
    //如果字节流是从内存流向设备（如显示屏、打印机、磁盘驱动器、网络连接等），这叫做输出操作。
}

//7.1.1.2 I/O 库头文件
void test7_1_1_2() {
    //<iostream>该文件定义了 cin、cout、cerr 和 clog 对象，分别对应于标准输入流、标准输出流、非缓冲标准错误流和缓冲标准错误流。
    //<iomanip>	该文件通过所谓的参数化的流操纵器（比如 setw 和 setprecision），来声明对执行标准化 I/O 有用的服务。
    //<fstream>	该文件为用户控制的文件处理声明服务。
}

//7.1.1.3 标准输出流（cout）
void test7_1_1_3() {
    //预定义的对象 cout 是 iostream 类的一个实例。cout 对象"连接"到标准输出设备，通常是显示屏。cout 是与流插入运算符 << 结合使用的，如下所示：
    char str[] = "Hello C++";
    cout << "Value of str is : " << str << endl;
    //C++ 编译器根据要输出变量的数据类型，选择合适的流插入运算符来显示值。<< 运算符被重载来输出内置类型（整型、浮点型、double 型、字符串和指针）的数据项。
}

//7.1.1.4 标准输入流（cin）
void test7_1_1_4() {
    char name[50];
    cout << "请输入您的名称" ;
    cin >> name;
    cout << "您的名称是： " << name << endl;
    //C++ 编译器根据要输入值的数据类型，选择合适的流提取运算符来提取值，并把它存储在给定的变量中。
    //流提取运算符 >> 在一个语句中可以多次使用，如果要求输入多个数据，可以使用如下语句：
    int age;
    cout << "请输入您的名字和年龄：";
    cin >> name >> age;
    cout << "您的名字和年龄： " << name << " , " << age << endl;
}

//7.1.1.5 标准错误流（cerr）
void test7_1_1_5() {
    //预定义的对象 cerr 是 iostream 类的一个实例。cerr 对象附属到标准输出设备，通常也是显示屏，但是 cerr 对象是非缓冲的，且每个流插入到 cerr 都会立即输出。
    //cerr 也是与流插入运算符 << 结合使用的，如下所示：
    char str[] = "Unable to read……";
    cerr << "Error message : " << str << endl;
}


//7.1.1.6 标准日志流（clog）
void test7_1_1_6() {
    //预定义的对象 clog 是 iostream 类的一个实例。clog 对象附属到标准输出设备，通常也是显示屏，但是 clog 对象是缓冲的。
    //这意味着每个流插入到 clog 都会先存储在缓冲区，直到缓冲填满或者缓冲区刷新时才会输出。
    //clog 也是与流插入运算符 << 结合使用的，如下所示：
    char str[] = "Unable to read....";
    clog << "Error message : " << str << endl;
}






