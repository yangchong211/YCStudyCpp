//
// Created by 杨充 on 2023/7/5.
//



#include <iostream>
#include <csignal>
#include <unistd.h>
using namespace std;

//15.2.1.1 什么是信号处理
void test15_2_1_1();
//15.2.1.2 信号处理案例
void test15_2_1_2();
//15.2.1.3 signal() 函数
void test15_2_1_3();
//15.2.1.4 raise() 函数
void test15_2_1_4();



int main() {
//    test15_2_1_1();
//    test15_2_1_2();
//    test15_2_1_3();
    test15_2_1_4();
    return 0;
}



//15.2.1.1 什么是信号处理
void test15_2_1_1() {
    //C++ 信号处理
    //信号是由操作系统传给进程的中断，会提早终止一个程序。在 UNIX、LINUX、Mac OS X 或 Windows 系统上，可以通过按 Ctrl+C 产生中断。
    //有些信号不能被程序捕获，但是下表所列信号可以在程序中捕获，并可以基于信号采取适当的动作。这些信号是定义在 C++ 头文件 <csignal> 中。

    //SIGABRT	程序的异常终止，如调用 abort。
    //SIGFPE	错误的算术运算，比如除以零或导致溢出的操作。
    //SIGILL	检测非法指令。
    //SIGINT	程序终止(interrupt)信号。
    //SIGSEGV	非法访问内存。
    //SIGTERM	发送到程序的终止请求。
    cout << "15.2.1.1 什么是信号处理" << endl;
}

void signalHandlerA(int signum) {
    std::cout << "Received signal: " << signum << std::endl;
}

//15.2.1.2 信号处理案例
void test15_2_1_2() {
    //在C++中，信号处理（Signal Handling）是一种处理异步事件的机制。
    //信号是由操作系统或其他进程发送给进程的消息，用于通知进程发生了某种事件，如中断、错误或其他特定条件。
    //C++提供了一组函数和类型来处理信号。以下是一些常用的信号处理函数和类型：
    //signal()函数：用于设置信号处理函数。它接受两个参数，第一个参数是要处理的信号，第二个参数是信号处理函数的指针。
    //SIG_IGN：表示忽略信号。可以将该值作为信号处理函数的指针传递给signal()函数，以忽略特定的信号。
    //SIG_DFL：表示使用默认的信号处理方式。可以将该值作为信号处理函数的指针传递给signal()函数，以使用系统默认的信号处理方式。
    //raise()函数：用于向当前进程发送信号。它接受一个参数，表示要发送的信号。
    cout << "15.2.1.2 信号处理案例" << endl;
    // 设置信号处理函数
    signal(SIGINT, signalHandlerA);
    std::cout << "Running..." << std::endl;
    // 发送信号
    raise(SIGINT);
    //在上述示例中，我们定义了一个名为 signalHandlerA 的信号处理函数，它会在接收到SIGINT信号（即键盘中断信号）时被调用。
    //在函数中，我们使用signal()函数将SIGINT信号与 signalHandlerA 函数关联起来。然后，我们使用raise()函数发送SIGINT信号。

    //Running...
    //Received signal: 2

    //当程序运行时，它会输出"Running..."，然后在接收到SIGINT信号时，会调用 signalHandlerA 函数，并输出"Received signal: 2"（2是SIGINT的信号编号）。
}



//使用 signal() 函数捕获 SIGINT 信号。不管您想在程序中捕获什么信号，您都必须使用 signal 函数来注册信号，并将其与信号处理程序相关联。看看下面的实例：
void signalHandler( int signum ){
    cout << "Interrupt signal (" << signum << ") received.\n";
    // 清理并关闭
    // 终止程序
    exit(signum);
}

//15.2.1.3 signal() 函数
void test15_2_1_3() {
    //#include <csignal>
    //void (*signal(int signum, void (*handler)(int)))(int);

    //signal()函数接受两个参数：
    //signum：表示要设置处理函数的信号编号。可以使用预定义的宏（如SIGINT、SIGTERM等）或信号编号来指定信号。
    //handler：表示要设置的信号处理函数的指针。可以是函数指针，也可以是SIG_IGN（忽略信号）或SIG_DFL（使用默认处理方式）。
    cout << "15.2.1.3 signal() 函数" << endl;
    // 注册信号 SIGINT 和信号处理程序
    //signal()函数的返回值是一个指向之前信号处理函数的指针。如果之前没有设置过信号处理函数，则返回SIG_DFL或SIG_IGN。
    signal(SIGINT, signalHandler);
    while(1){
        cout << "Going to sleep...." << endl;
        //功能：执行挂起一段时间，也就是等待一段时间在继续执行
        sleep(1);
    }

    //现在，按 Ctrl+C 来中断程序，您会看到程序捕获信号，程序打印如下内容并退出：
    //Going to sleep....
    //Going to sleep....
    //Going to sleep....
    //Interrupt signal (2) received.
}


//15.2.1.4 raise() 函数
void test15_2_1_4() {
    //raise() 函数
    //您可以使用函数 raise() 生成信号，该函数带有一个整数信号编号作为参数，语法如下：int raise (signal sig);
    //在这里，sig 是要发送的信号的编号，这些信号包括：SIGINT、SIGABRT、SIGFPE、SIGILL、SIGSEGV、SIGTERM、SIGHUP。
    //以下是我们使用 raise() 函数内部生成信号的实例：
    cout << "15.2.1.4 raise() 函数" << endl;
    int i = 0;
    // 注册信号 SIGINT 和信号处理程序
    signal(SIGINT, signalHandler);
    while(++i){
        cout << "Going to sleep...." << endl;
        if( i == 3 ){
            //signum：表示要发送的信号编号。可以使用预定义的宏（如SIGINT、SIGTERM等）或信号编号来指定信号。
            int status = raise( SIGINT);
            //raise()函数返回一个整数值，表示函数执行的结果。如果成功发送信号，则返回0；否则，返回非零值。
            cout << "raise return " << status << endl;
        }
        sleep(1);
    }

    //15.2.1.4 raise() 函数
    //Going to sleep....
    //Going to sleep....
    //Going to sleep....
    //Interrupt signal (2) received.
}

