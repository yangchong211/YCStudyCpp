//
// Created by 杨充 on 2023/6/19.
//


//C++ 文件和流
//到目前为止，我们已经使用了 iostream 标准库，它提供了 cin 和 cout 方法分别用于从标准输入读取流和向标准输出写入流。

#include <fstream>
#include <iostream>
#include <fcntl.h>
#include <unistd.h>

using namespace std;
//iostream标准库，fstream读写文件
void test1();
//fcntl标准库，
void test2();

int main() {
    test1();
    test2();
    return 0;
}

void test1() {
    char data[100];
    ofstream outfile;
    //打开文件
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    outfile.open("yc.txt");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: \n";
    cin.getline(data, 100);




    // 向文件写入用户输入的数据
    // 使用流插入运算符（ << ）向文件写入信息，就像使用该运算符输出信息到屏幕上一样。
    // 唯一不同的是，在这里您使用的是 ofstream 或 fstream 对象，而不是 cout 对象。
    outfile << data << endl;
    cout << "Enter your age: ";
    cin >> data;
    cin.ignore();

    // 再次向文件写入用户输入的数据
    outfile << data << endl;

    // 关闭打开的文件
    //关闭文件
    //当 C++ 程序终止时，它会自动关闭刷新所有流，释放所有分配的内存，并关闭所有打开的文件。但程序员应该养成一个好习惯，在程序终止前关闭所有打开的文件。
    //下面是 close() 函数的标准语法，close() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    outfile.close();


    // 以读模式打开文件
    ifstream infile;
    infile.open("yc.txt");
    cout << "Reading from the file" << endl;
    // 使用流提取运算符（ >> ）从文件读取信息，就像使用该运算符从键盘输入信息一样。
    // 唯一不同的是，在这里您使用的是 ifstream 或 fstream 对象，而不是 cin 对象。
    infile >> data;
    cout << data << endl;

    infile >> data;
}


void test2() {
    //fcntl.h头文件中的open函数是C语言中用于打开文件的函数。它提供了对文件的访问和操作的功能。
    //pathname：要打开的文件的路径名。
    //flags：打开文件的标志，用于指定文件的打开方式和操作选项。例如，O_RDONLY表示只读打开，O_WRONLY表示只写打开，O_RDWR表示读写打开，O_CREAT表示如果文件不存在则创建文件等。
    //mode：当使用O_CREAT标志创建新文件时，指定文件的权限。它是一个八进制数，例如0644表示文件权限为rw-r--r--。
    int buffer_fd = open("yc.txt", O_RDWR | O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    cout << "buffer_fd " << buffer_fd << endl;
    //buffer_fd 4
    if (buffer_fd == -1) {
        //我们使用open函数打开名为"example.txt"的文件，以只写方式打开，并在需要时创建文件。如果打开文件失败，我们使用perror函数打印错误信息。
        perror("open");
    }
    // 写入文件
    const char *message = "Hello, World! doubi , i am yangchong";
    write(buffer_fd, message, strlen(message));
    // 关闭文件
    close(buffer_fd);
}