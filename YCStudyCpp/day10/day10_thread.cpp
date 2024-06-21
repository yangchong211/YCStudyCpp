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


//c++ 11 之后有了标准的线程库：std::thread。
//之前一些编译器使用 C++11 的编译参数是 -std=c++11
//g++ -std=c++11 day10_thread.cpp

using namespace std;

//10.1.2.1 std::thread 构造函数
void test10_1_2_1();
//10.1.2.2 std::thread 赋值操作
void test10_1_2_2();
//10.1.2.3 std::thread 获取线程id
void test10_1_2_3();
//10.1.2.4 std::thread joinable
void test10_1_2_4();


int main() {
    test10_1_2_1();
    test10_1_2_2();
//    test10_1_2_3();
    test10_1_2_4();
    return 0;
}

//一个可调用对象可以是以下三个中的任何一个：
//函数指针
//函数对象
//lambda 表达式
void x1(int n) {
    for (int i = 0; i < 5; ++i) {
        cout << "Thread " << n << " executing\n";
        this_thread::sleep_for(chrono::milliseconds(1000));
    }
}

void x2(int &n) {
    for (int i = 0; i < 5; ++i) {
        std::cout << "Thread 2 executing\n";
        ++n;
        this_thread::sleep_for(chrono::milliseconds(1000));
    }
}


//10.1.2.1 std::thread 构造函数
void test10_1_2_1() {
    //默认构造函数	thread() noexcept;
    //初始化构造函数	template <class Fn, class... Args>
    //        explicit thread(Fn&& fn, Args&&... args);
    //拷贝构造函数 [deleted]	thread(const thread&) = delete;
    //Move 构造函数	thread(thread&& x) noexcept;

    //默认构造函数，创建一个空的 std::thread 执行对象。
    //初始化构造函数，创建一个 std::thread 对象，该 std::thread 对象可被 joinable，新产生的线程会调用 fn 函数，该函数的参数由 args 给出。
    //拷贝构造函数(被禁用)，意味着 std::thread 对象不可拷贝构造。
    //Move 构造函数，move 构造函数(move 语义是 C++11 新出现的概念，详见附录)，调用成功之后 x 不代表任何 std::thread 执行对象。
    int n = 0;
    thread t1;          //默认构造函数
    thread t2(x1,n+1);  //初始化构造函数
    thread t3(x2, ref(n));  //拷贝构造函数
    thread t4(std::move(t3));        //Move 构造函数
    t2.join();
    t4.join();
    std::cout << "Final value of n is " << n << '\n';
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

//10.1.2.2 std::thread 赋值操作
void test10_1_2_2() {
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


void async_log_thread() {
    cout << "使用std::thread线程方法被执行" << endl;
    std::this_thread::sleep_for(chrono::seconds(1));
}

//10.1.2.3 std::thread 获取线程id
void test10_1_2_3() {
    cout << "使用std::thread创建线程" << endl;
    //std::thread 创建线程
    thread async_thread = std::thread(async_log_thread);
    //线程休眠10秒钟
    //sleep_for: 线程休眠某个指定的时间片(time span)，该线程才被重新唤醒
    std::this_thread::sleep_for(std::chrono::milliseconds(10 * 1000));
    //获取线程id，get_id: 获取线程 ID，返回一个类型为 std::thread::id 的对象。
    thread::id id = async_thread.get_id();
    cout << "获取线程id " << id << endl;
    //任务执行
//    async_thread.join();
    //这个是销毁
//    async_thread.detach();
}


//10.1.2.4 std::thread joinable
void test10_1_2_4() {
    std::thread t;
    std::cout << "before starting, joinable: " << t.joinable() << '\n';
    t = std::thread(async_log_thread);
    std::cout << "after starting, joinable: " << t.joinable() << '\n';
    t.join();
}






