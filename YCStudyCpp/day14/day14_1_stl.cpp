//
// Created by 杨充 on 2024/6/21.
//
#include <iostream>

using namespace std;

//14.3.1.1 输入迭代器（Input Iterator）
void test14_3_1_1();
//14.3.1.2 输出迭代器（Output Iterator）



int main() {
    test14_3_1_1();
    return 0;
}


//14.3.1.1 输入迭代器（Input Iterator）
void test14_3_1_1() {
    std::cout << "14.3.1.1 输入迭代器（Input Iterator）" << std::endl;
    //在C++ STL中，输入迭代器（Input Iterator）是一种迭代器类型，它允许您从容器中读取元素，并且只能向前移动。输入迭代器提供了一种一次性遍历容器元素的方式。
    //
    //输入迭代器的主要特点如下：
    //
    //可以使用解引用操作符*来访问当前迭代器指向的元素。
    //可以使用递增操作符++将迭代器向前移动到下一个元素。
    //不支持递减操作符--和随机访问操作符（如+和-）。
    //可以使用相等操作符==和!=来比较两个迭代器是否相等。

    std::vector<int> number = {1,2,3,4,5};
    for (auto i = number.begin(); i != number.end(); ++i) {
        std::cout << * i << " ";
    }
    std::cout << std::endl;
}






