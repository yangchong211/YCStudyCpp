//
// Created by 杨充 on 2023/6/20.
//


//C++ 命名空间
//假设这样一种情况，当一个班上有两个名叫 Zara 的学生时，为了明确区分它们，我们在使用名字之外，不得不使用一些额外的信息
//比如他们的家庭住址，或者他们父母的名字等等。

#include <iostream>
using namespace std;

void test1();
void test2();

int main() {
    test1();
    test2();
    return 0;
}

namespace first_space{
    void func(){
        cout << "Inside first_space" << endl;
    }
}

namespace second_space{
    void func(){
        cout << "Inside second_space" << endl;
    }
}

void test1() {
    // 调用第一个命名空间中的函数
    first_space::func();
    // 调用第二个命名空间中的函数
    second_space::func();
}

//您可以使用 using namespace 指令，这样在使用命名空间时就可以不用在前面加上命名空间的名称。
//这个指令会告诉编译器，后续的代码将使用指定的命名空间中的名称。
using namespace first_space;

void test2() {
    func();
}

// 第一个命名空间
namespace first_space1{
    void func(){
        cout << "Inside first_space1" << endl;
    }
    // 第二个命名空间
    namespace second_space1{
        void func(){
            cout << "Inside second_space1" << endl;
        }
    }
}





