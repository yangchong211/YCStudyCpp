//
// Created by 杨充 on 2024/5/24.
//
#include <iostream>
#include <csignal>
#include <unistd.h>
using namespace std;

//10.1.1.1 创建线程
void test10_1_1_1();


int main() {
    test10_1_1_1();
    return 0;
}

void *say_hello(void *args) {
    if (args != NULL) {
        string str = *((string *) args);
        cout << "Hello Yc 1！" << str << endl;
    } else {
        cout << "Hello Yc 2！" << endl;
    }
    return 0;
}


//10.1.1.1 创建线程
void test10_1_1_1() {
    //在这里，pthread_create 创建一个新的线程，并让它可执行。下面是关于参数的说明：
    //参数	描述
    //thread	指向线程标识符指针。
    //attr	一个不透明的属性对象，可以被用来设置线程属性。您可以指定线程属性对象，也可以使用默认值 NULL。
    //start_routine	线程运行函数起始地址，一旦线程被创建就会执行。
    //arg	运行函数的参数。它必须通过把引用作为指针强制转换为 void 类型进行传递。如果没有传递参数，则使用 NULL。
    pthread_t thread;
    int ret1 = pthread_create(&thread , NULL , say_hello , (void *)&"yc dou bi");
    //注意，最后一个传入参数，必须使用 & ，否则参数无法传递
    //int ret = pthread_create(&thread,NULL, say_hello , (void *)"yc dou bi");
    //创建线程成功时，函数返回 0，若返回值不为 0 则说明创建线程失败。
    if (ret1 != 0) {
        cout << "pthread_create error: error_code=" << ret1 << endl;
    } else {
        cout << "pthread_create success: " << ret1 << endl;
    }
    //pthread_exit 用于显式地退出一个线程。
    //pthread_exit(thread);
}





