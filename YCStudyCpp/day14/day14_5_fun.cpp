//
// Created by 杨充 on 2024/6/24.
//

#include <iostream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>

using namespace std;


//14.5.1.1 函数指针：可以将函数指针作为函数对象使用
void test14_5_1_1();
//14.5.1.2 函数对象类
void test14_5_1_2();
//14.5.1.3 Lambda表达式
void test14_5_1_3();


int main() {
    test14_5_1_1();
    test14_5_1_2();
    test14_5_1_3();
    return 0;
}

bool compare(int a, int b) {
    return a < b;
}

//14.5.1.1 函数指针：可以将函数指针作为函数对象使用
void test14_5_1_1() {
    //在上述示例中，compare函数是一个函数指针，用作std::sort算法的比较函数对象。
    std::sort(numbers.begin(), numbers.end(), compare);
}


struct Compare {
    bool operator()(int a, int b) const {
        return a < b;
    }
};

//14.5.1.2 函数对象类
void test14_5_1_2() {
    //函数对象类：可以定义一个类，重载operator()运算符，使其成为一个可调用对象。例如：
    std::sort(numbers.begin(), numbers.end(), Compare());
    //在上述示例中，Compare是一个函数对象类，重载了operator()运算符，使其成为一个可调用对象。
    //我们创建了一个Compare对象，并将其作为std::sort算法的比较函数对象。
    //函数对象类还可以带有成员变量，这些变量可以在调用时使用。这使得函数对象非常灵活，可以在算法中存储状态。
}

//14.5.1.3 Lambda表达式
void test14_5_1_3() {
    //Lambda表达式：Lambda表达式是一种匿名函数，可以直接在需要函数对象的地方定义和使用。例如：
    std::sort(numbers.begin(), numbers.end(), [](int a, int b) {
        return a < b;
    });
}



