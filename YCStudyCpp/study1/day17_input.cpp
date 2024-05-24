//
// Created by 杨充 on 2023/6/8.
//

//C++ 基本的输入输出
//C++ 的 I/O 发生在流中，流是字节序列。
//如果字节流是从设备（如键盘、磁盘驱动器、网络连接等）流向内存，这叫做输入操作。
//如果字节流是从内存流向设备（如显示屏、打印机、磁盘驱动器、网络连接等），这叫做输出操作。

//<iostream>该文件定义了 cin、cout、cerr 和 clog 对象，分别对应于标准输入流、标准输出流、非缓冲标准错误流和缓冲标准错误流。
//<iomanip>	该文件通过所谓的参数化的流操纵器（比如 setw 和 setprecision），来声明对执行标准化 I/O 有用的服务。
//<fstream>	该文件为用户控制的文件处理声明服务。

#include <iostream>
using namespace std;

//通过这些小实例，我们无法区分 cout、cerr 和 clog 的差异，但在编写和执行大型程序时，它们之间的差异就变得非常明显。
//所以良好的编程实践告诉我们，使用 cerr 流来显示错误消息，而其他的日志消息则使用 clog 流来输出。
//标准输出流（cout）
void test1();
//标准输入流（cin）
void test2();
//标准错误流（cerr）
void test3();
//标准日志流（clog）
void test4();


int main(){
    test1();
    test2();
    test3();
    test4();
    return 0;
}

//标准输出流（cout）
//预定义的对象 cout 是 iostream 类的一个实例。cout 对象"连接"到标准输出设备，通常是显示屏。cout 是与流插入运算符 << 结合使用的，如下所示：
void test1(){
    char str[] = "Hello C++";
    cout << "Value of str is : " << str << endl;
}

//标准输入流（cin）
//预定义的对象 cin 是 iostream 类的一个实例。cin 对象附属到标准输入设备，通常是键盘。cin 是与流提取运算符 >> 结合使用的，如下所示：
void test2(){
    char name[50];
    int age;
    cout << "请输入您的名称:";
    cin >> name;
    cout << "您的名称是： " << name << endl;
    cout << "请输入您的名字和年龄：";
    cin >> name >> age;
    cout << "您的名字和年龄： " << name << age << endl;
}

//标准错误流（cerr）
//预定义的对象 cerr 是 iostream 类的一个实例。cerr 对象附属到标准输出设备，通常也是显示屏，但是 cerr 对象是非缓冲的，且每个流插入到 cerr 都会立即输出。
//cerr 也是与流插入运算符 << 结合使用的，如下所示：
void test3(){
    char str[] = "Unable to read……";
    cerr << "Error message : " << str << endl;
}

//标准日志流（clog）
//预定义的对象 clog 是 iostream 类的一个实例。clog 对象附属到标准输出设备，通常也是显示屏，但是 clog 对象是缓冲的。
//这意味着每个流插入到 clog 都会先存储在缓冲区，直到缓冲填满或者缓冲区刷新时才会输出。
//clog 也是与流插入运算符 << 结合使用的，如下所示：
void test4(){
    char str[] = "Unable to read....";
    clog << "Error message : " << str << endl;
}