//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
#include <unistd.h>
#include <cstdlib>
#include <pthread.h>
#include "thread"
#include <utility>
#include <chrono>
#include <functional>


//C++ 多线程
//多线程是多任务处理的一种特殊形式，多任务处理允许让电脑同时运行两个或两个以上的程序。一般情况下，两种类型的多任务处理：基于进程和基于线程。
//基于进程的多任务处理是程序的并发执行。
//基于线程的多任务处理是同一程序的片段的并发执行。

using namespace std;

//pthread_create 创建单个线程，pthread_exit 销毁线程
void test1();

//pthread_create 创建多线程
void test2();

//pthread_t 向线程传递参数
void test3();

//pthread_t 连接和分离线程
//pthread_join (threadid, status)
//pthread_detach (threadid)
void test4();

//std::thread，构造函数
void test5();

//std::thread 赋值操作
void test6();

int main() {
    //test1();
    //test2();
    //test3();
    //test4();
    //test5();
    test6();
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


//创建线程  pthread_create 创建一个新的线程，并让它可执行。
//终止线程  pthread_exit
void test1() {
    pthread_t thread;
    //参数依次是：创建的线程，线程参数，调用的函数，传入的函数参数
    //hread	        指向线程标识符指针。
    //attr	        一个不透明的属性对象，可以被用来设置线程属性。您可以指定线程属性对象，也可以使用默认值 NULL。
    //start_routine	线程运行函数起始地址，一旦线程被创建就会执行。
    //arg	        运行函数的参数。它必须通过把引用作为指针强制转换为 void 类型进行传递。如果没有传递参数，则使用 NULL。
    int ret1 = pthread_create(&thread, NULL, say_hello, (void *) &"yc dou bi");
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


#define NUM_THREADS 5

void test2() {
    // 定义线程的 id 变量，多个变量使用数组
    pthread_t tids[NUM_THREADS];
    for (int i = 0; i < NUM_THREADS; ++i) {
        //参数依次是：创建的线程id，线程参数，调用的函数，传入的函数参数
        int ret = pthread_create(&tids[i], NULL, say_hello, NULL);
        if (ret != 0) {
            cout << "pthread_create error: error_code=" << ret << endl;
        }
    }

    //等各个线程退出后，进程才结束，否则进程强制结束了，线程可能还没反应过来；
    //在这里，pthread_exit 用于显式地退出一个线程。通常情况下，pthread_exit() 函数是在线程完成工作后无需继续存在时被调用。
    //如果 main() 是在它所创建的线程之前结束，并通过 pthread_exit() 退出，那么其他线程将继续执行。否则，它们将在 main() 结束时自动被终止。
    pthread_exit(NULL);
}

//定义一个结构体
struct thread_data {
    int thread_id;
    char *message;
};


//这里为何要*
void *printHello(void *thread_arg) {
    struct thread_data *my_data;
    my_data = (struct thread_data *) thread_arg;
    cout << "Thread ID : " << my_data->thread_id;
    cout << " Message : " << my_data->message << endl;
    pthread_exit(NULL);
}


//向线程传递参数
void test3() {
    pthread_t threads[NUM_THREADS];
    struct thread_data td[NUM_THREADS];
    int rc;
    for (int i = 0; i < NUM_THREADS; i++) {
        cout << "main : create thread " << i << endl;
        td[i].thread_id = i;
        td[i].message = (char *) "this is message";
        rc = pthread_create(&threads[i], NULL, printHello, (void *) &td[i]);
        if (rc) {
            cout << "Error:unable to create thread," << rc << endl;
            exit(-1);
        }
    }
    pthread_exit(NULL);
}


void *wait(void *t) {
    int i;
    long tid;
    tid = (long) t;
    sleep(1);
    cout << "Sleeping in thread " << endl;
    cout << "Thread with id : " << tid << "  ...exiting " << endl;
    pthread_exit(NULL);
}


void test4() {
    int rc;
    int i;
    pthread_t threads[NUM_THREADS];
    pthread_attr_t attr;
    void *status;

    // 初始化并设置线程为可连接的（joinable）
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);

    for (i = 0; i < NUM_THREADS; i++) {
        cout << "main() : creating thread, " << i << endl;
        rc = pthread_create(&threads[i], NULL, wait, (void *) &i);
        if (rc) {
            cout << "Error:unable to create thread," << rc << endl;
            exit(-1);
        }
    }

    // 删除属性，并等待其他线程
    pthread_attr_destroy(&attr);
    for (i = 0; i < NUM_THREADS; i++) {
        rc = pthread_join(threads[i], &status);
        if (rc) {
            cout << "Error:unable to join," << rc << endl;
            exit(-1);
        }
        cout << "Main: completed thread id :" << i;
        cout << "  exiting with status :" << status << endl;
    }

    cout << "Main: program exiting." << endl;
    pthread_exit(NULL);
}


//std::thread thread_object(callable)
//一个可调用对象可以是以下三个中的任何一个：
//函数指针
//函数对象
//lambda 表达式
void f1(int n) {
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread " << n << " executing\n";
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void f2(int &n) {
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread 2 executing\n";
        ++n;
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void async_log_thread() {
    cout << "使用std::thread线程方法被执行" << endl;
}


void test5() {
    cout << "线程 1 、2 、3 独立运行" << endl;
    int n = 0;
    std::thread t1; // t1 is not a thread
    std::thread t2(f1, n + 1); // pass by value
    std::thread t3(f2, std::ref(n)); // pass by reference
    std::thread t4(std::move(t3)); // t4 is now running f2(). t3 is no longer a thread
    t2.join();
    t4.join();
    std::cout << "Final value of n is " << n << '\n';

    cout << "使用std::thread创建线程" << endl;
    //std::thread 创建线程
    std::thread async_thread = std::thread(async_log_thread);
    //线程休眠10秒钟
    //sleep_for: 线程休眠某个指定的时间片(time span)，该线程才被重新唤醒
    std::this_thread::sleep_for(std::chrono::milliseconds(10 * 1000));
    cout << "线程休眠10秒钟" << endl;
    //获取线程id，get_id: 获取线程 ID，返回一个类型为 std::thread::id 的对象。
    std::thread::id t1_id = async_thread.get_id();
    cout << "获取线程id " << t1_id << endl;
    //任务执行
    async_thread.join();
    //这个是销毁
    async_thread.detach();
}


//std::thread 赋值操作
//Move 赋值操作(1)，如果当前对象不可 joinable，需要传递一个右值引用(rhs)给 move 赋值操作；如果当前对象可被 joinable，则会调用 terminate() 报错。
//拷贝赋值操作(2)，被禁用，因此 std::thread 对象不可拷贝赋值。
void thread_task(int n) {
    std::this_thread::sleep_for(std::chrono::seconds(n));
    std::cout << "hello thread "
              << std::this_thread::get_id()
              << " paused " << n << " seconds" << std::endl;
}


void test6() {
    std::thread threads[5];
    std::cout << "Spawning 5 threads...\n";
    for (int i = 0; i < 5; i++) {
        threads[i] = std::thread(thread_task, i + 1);
    }
    std::cout << "Done spawning threads! Now wait for them to join\n";
    for (std::thread &t: threads) {
        t.join();
    }
    std::cout << "All threads joined.\n";
}


