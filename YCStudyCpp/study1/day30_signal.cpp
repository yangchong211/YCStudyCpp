//
// Created by 杨充 on 2023/7/5.
//

//C++ 信号处理
//信号是由操作系统传给进程的中断，会提早终止一个程序。在 UNIX、LINUX、Mac OS X 或 Windows 系统上，可以通过按 Ctrl+C 产生中断。
//有些信号不能被程序捕获，但是下表所列信号可以在程序中捕获，并可以基于信号采取适当的动作。这些信号是定义在 C++ 头文件 <csignal> 中。

//SIGABRT	程序的异常终止，如调用 abort。
//SIGFPE	错误的算术运算，比如除以零或导致溢出的操作。
//SIGILL	检测非法指令。
//SIGINT	程序终止(interrupt)信号。
//SIGSEGV	非法访问内存。
//SIGTERM	发送到程序的终止请求。

#include <iostream>
#include <csignal>
#include <unistd.h>
using namespace std;

void test1();
void test2();
void test3();
void test4();

int main() {
    //test1();
    //test2();
    test3();
    test4();
    return 0;
}

//使用 signal() 函数捕获 SIGINT 信号。不管您想在程序中捕获什么信号，您都必须使用 signal 函数来注册信号，并将其与信号处理程序相关联。看看下面的实例：
void signalHandler( int signum ){
    cout << "Interrupt signal (" << signum << ") received.\n";
    // 清理并关闭
    // 终止程序
    exit(signum);
}

//C++ 信号处理库提供了 signal 函数，用来捕获突发事件。以下是 signal() 函数的语法：
void test1(){
    // 注册信号 SIGINT 和信号处理程序
    //这个函数接收两个参数：第一个参数是要设置的信号的标识符，第二个参数是指向信号处理函数的指针。
    //函数返回值是一个指向先前信号处理函数的指针。如果先前没有设置信号处理函数，则返回值为 SIG_DFL。
    //如果先前设置的信号处理函数为 SIG_IGN，则返回值为 SIG_IGN。
    signal(SIGINT, signalHandler);
    while(1){
        cout << "Going to sleep...." << endl;
        //功能：执行挂起一段时间，也就是等待一段时间在继续执行
        sleep(1);
    }
}

//raise() 函数
//您可以使用函数 raise() 生成信号，该函数带有一个整数信号编号作为参数，语法如下：int raise (signal sig);
//在这里，sig 是要发送的信号的编号，这些信号包括：SIGINT、SIGABRT、SIGFPE、SIGILL、SIGSEGV、SIGTERM、SIGHUP。
//以下是我们使用 raise() 函数内部生成信号的实例：
void test2(){
    int i = 0;
    // 注册信号 SIGINT 和信号处理程序
    signal(SIGINT, signalHandler);
    while(++i){
        cout << "Going to sleep...." << endl;
        if( i == 3 ){
            raise( SIGINT);
        }
        sleep(1);
    }

    //Going to sleep....
    //Going to sleep....
    //Going to sleep....
    //Interrupt signal (2) received.
}

void test3(){

}

void test4(){

}


