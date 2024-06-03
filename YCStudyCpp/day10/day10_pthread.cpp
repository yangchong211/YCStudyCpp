//
// Created by 杨充 on 2024/5/24.
//
#include <iostream>
#include <csignal>
#include <unistd.h>
using namespace std;

#define NUM_THREADS 5
//10.1.1.1 创建线程
void test10_1_1_1();
//10.1.1.2 终止线程
void test10_1_1_2();
//10.1.1.3 取消线程
void test10_1_1_3();
//10.1.1.4 向线程传递参数
void test10_1_1_4();
//10.1.1.5 连接线程
void test10_1_1_5();
//10.1.1.6 分离线程
void test10_1_1_6();


int main() {
    test10_1_1_1();
//    test10_1_1_2();
//    test10_1_1_3();
//    test10_1_1_4();
//    test10_1_1_5();
    test10_1_1_6();
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
    std::cout << "Thread test10_1_1_1 创建线程 " << std::endl;
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


//10.1.1.2 终止线程
void test10_1_1_2() {
    std::cout << "Thread test10_1_1_2 终止线程 " << std::endl;
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

void * thread1_3() {
    std::cout << "Thread is running." << std::endl;
    while (true) {
//        if (0 != pthread_testcancel()) {
//            std::cout << "Thread is canceled." << std::endl;
//            break;
//        }

        // 线程执行的逻辑
        // ...
    }
    pthread_exit(NULL);
}

//10.1.1.3 取消线程
void test10_1_1_3() {
    std::cout << "Thread test10_1_1_3 取消线程 " << std::endl;
    //使用pthread_cancel函数：可以使用pthread_cancel函数向指定线程发送取消请求，使其终止执行。
    //被取消的线程需要在适当的地方检查取消请求，并进行相应的清理工作。以下是一个示例代码：
//    pthread_t thread;
//    int ret = pthread_create(&thread, NULL, thread1_3, NULL);
//    // 等待一段时间后取消线程
//    sleep(2);
//    pthread_cancel(thread);
//    pthread_join(thread, NULL);
//    std::cout << "Thread is terminated." << std::endl;
    //创建了一个线程，并在主线程中等待2秒后使用pthread_cancel函数取消线程。被取消的线程在适当的地方检查取消请求，并在取消请求发生时终止执行。
}


struct thread_data {
    int thread_id;
    char *message;
};

void *printHello(void *thread_arg) {
    struct thread_data *my_data;
    my_data = (struct thread_data*)thread_arg;
    //my_data = static_cast<thread_data *>(thread_arg);
    cout << "Thread ID : " << my_data->thread_id;
    cout << " Message : " << my_data->message << endl;
    pthread_exit(NULL);
}


//10.1.1.4 向线程传递参数
void test10_1_1_4() {
    std::cout << "Thread test10_1_1_4 " << std::endl;
    pthread_t pthread;
    struct thread_data td;
    td.thread_id = 1;
//    td.message = "打工充";
    int ret = pthread_create(&pthread, NULL ,printHello ,&td);
    if (ret){
        cout << "Error:unable to create thread," << ret << endl;
        exit(-1);
    }
    pthread_exit(NULL);
}


void *testFun5(void * arg){
    int *value = new int(42);
    return static_cast<void*>(value);
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

//10.1.1.5 连接线程
void test10_1_1_5() {
    std::cout << "Thread test10_1_1_5 " << std::endl;
    //int pthread_join(pthread_t thread, void **retval);
    //其中，thread参数是要等待的线程的标识符，retval参数是一个指向指针的指针，用于接收线程的返回值。
    //使用pthread_join函数的步骤如下：
    //创建线程：首先，使用pthread_create函数创建一个线程，并传递线程函数和参数。
    //等待线程结束：在主线程中，使用pthread_join函数等待线程的结束。主线程会阻塞在pthread_join函数处，直到指定的线程结束。
    //获取线程返回值（可选）：如果线程函数有返回值，可以通过retval参数来获取线程的返回值。需要注意的是，retval参数是一个指向指针的指针，因此需要使用双重指针来接收返回值。
    pthread_t pthread;
    pthread_create(&pthread,NULL,testFun5,NULL);
    void * result;
    pthread_join(pthread,&result);
    int* returnValue = static_cast<int*>(result);
    std::cout << "Thread returned: " << *returnValue << std::endl;
    //testFun 函数是线程的入口函数，它返回一个动态分配的整数值。
    //在主线程中，使用pthread_join函数等待线程的结束，并通过result参数获取线程的返回值。最后，释放返回值的内存。
    delete returnValue;



    int rc;
    int i;
    pthread_t threads[NUM_THREADS];
    pthread_attr_t attr;
    void *status;
    // 初始化并设置线程为可连接的（joinable）
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);

    for( i=0; i < NUM_THREADS; i++ ){
        cout << "main() : creating thread, " << i << endl;
        rc = pthread_create(&threads[i], NULL, wait, (void *)&i );
        if (rc){
            cout << "Error:unable to create thread," << rc << endl;
            exit(-1);
        }
    }

    // 删除属性，并等待其他线程
    pthread_attr_destroy(&attr);
    for( i=0; i < NUM_THREADS; i++ ){
        rc = pthread_join(threads[i], &status);
        if (rc){
            cout << "Error:unable to join," << rc << endl;
            exit(-1);
        }
        cout << "Main: completed thread id :" << i ;
        cout << "  exiting with status :" << status << endl;
    }

    cout << "Main: program exiting." << endl;
    pthread_exit(NULL);
}

void *testFun6(void * arg){
    std::cout << "Thread test10_1_1_6 running" << std::endl;
    return NULL;
}

//10.1.1.6 分离线程
void test10_1_1_6() {
    std::cout << "Thread test10_1_1_6 " << std::endl;
    //pthread_detach函数的原型如下：
    //int pthread_detach(pthread_t thread);
    //其中，thread参数是要设置为分离状态的线程的标识符。
    //使用pthread_detach函数的步骤如下：
    //创建线程：首先，使用pthread_create函数创建一个线程，并传递线程函数和参数。
    //设置线程为分离状态：在创建线程后，使用pthread_detach函数将线程设置为分离状态。一旦线程被设置为分离状态，它将在结束时自动释放资源。
    pthread_t thread;
    pthread_create(&thread, NULL, testFun6, NULL);
    pthread_detach(thread);
    // 继续执行其他操作...
}






