package com.liny.javastream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author linyi
 * @date 2022/9/1
 * 1.0
 * 中间操作符，终止操作符
 */
//@SpringBootTest
public class JavaStreamTest {

    /**
     * 作用：用于通过设置的条件过滤出元素
     * 即从集合中筛选出我们需要的（满足条件的）数据
     */
    @Test
    void filter() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> a = strings.stream().filter(str -> str.contains("a")).collect(Collectors.toList());
        System.out.println(a.toString()); //[abc, abcd]
    }

    /**
     * 返回一个元素各异（根据流所生成元素的hashCode和equals方法实现）的流。
     * 即 去重复。
     */
    @Test
    void distinct() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl","bc");
        List<String> collect = strings.stream().distinct().collect(Collectors.toList());
        System.out.println(collect); //[abc, , bc, efg, abcd, jkl]

        System.out.println("==============================================================");

        ArrayList<User> users = new ArrayList<>();
        User user01 = new User("liny",1);
        User user02 = new User("kang",2);
        User user03 = new User("jane",3);
        users.add(user01);
        users.add(user02);
        users.add(user03);
        users.add(user02);
        users.add(user01);
        List<User> collect1 = users.stream().distinct().collect(Collectors.toList());
        collect1.forEach(user-> System.out.println( user.toString()));
    }

    /**
     * 会返回一个不超过给定长度的流。
     */
    @Test
    void limit() {
        List<String> strings = Arrays.asList("abc", "abc", "bc", "efg", "abcd","jkl", "jkl");
        //设置返回一个长度为3的流
        List<String> limited = strings.stream().limit(3).collect(Collectors.toList());

        System.out.println(limited); //[abc, abc, bc]
    }

    /**
     * 返回一个扔掉了前n个元素的流
     */
    @Test
    void skip() {
        List<String> strings = Arrays.asList("abc", "abc", "bc", "efg", "abcd","jkl", "jkl");
        List<String> skiped = strings.stream().skip(3).collect(Collectors.toList());
        System.out.println(skiped); //[efg, abcd, jkl, jkl]
    }

    /**
     * 接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素
     * （使用映射一词，是因为它和转换类似，但其中的细微差别在于它是“创建一个新版本”而不是去“修改”）。
     */
    @Test
    void map() {
        ArrayList<User> users = new ArrayList<>();
        User user01 = new User("liny",1);
        User user02 = new User("kang",2);
        User user03 = new User("jane",3);
        users.add(user01);
        users.add(user02);
        users.add(user03);
        /**
         * 下面操作的生成一个新的集合List<UserDetail>
         *     其中UserDetail中包含User
         *     正常情况下我们需要遍历for循环去分别为其设置，现在使用Stream流可以便捷有效的实现。
         */
        List<UserDetail> collect = users.stream().map(stu -> {
            UserDetail userDetail = new UserDetail();
            userDetail.setUser(stu);
            userDetail.setHigh(183.5f);
            userDetail.setWeight(123.5f);
            return userDetail;
        }).collect(Collectors.toList());
        collect.forEach(sd->System.out.println(sd.toString()));
        /**
         * UserDetail(user=User(name=liny, id=1), high=183.5, weight=123.5)
         * UserDetail(user=User(name=kang, id=2), high=183.5, weight=123.5)
         * UserDetail(user=User(name=jane, id=3), high=183.5, weight=123.5)
         */
    }

    /**
     * 使用flatMap方法的效果是，各个数组并不是分别映射成一个流，而是映射成流的内容。
     * 所有使用map(Arrays::stream)时生成的单个流都被合并起来，即扁平化为一个流。
     */
    @Test
    void flatMap() {
        List<String> strings = Arrays.asList("abc", "abc", "bc", "efg", "abcd","jkl", "jkl");
        Stream<String> stringStream = strings.stream().map(x -> x);
        Stream<String> stringStream1 = strings.stream().flatMap(x -> Arrays.asList(x.split(" ")).stream());
    }

    /**
     * 返回排序后的流
     */
    @Test
    void sorted() {
        List<String> strings1 = Arrays.asList("abc", "abd", "aba", "efg", "abcd","jkl", "jkl");
        List<String> sorted1 = strings1.stream().sorted().collect(Collectors.toList());
        System.out.println(sorted1); //[aba, abc, abcd, abd, efg, jkl, jkl]
    }


    /**
     * 终止操作符
     */

    /**
     * 检查是否至少匹配一个元素，返回boolean
     */
    @Test
    void anyMatch(){

        List<String> strings = Arrays.asList("abc", "abd", "aba", "efg", "abcd","jkl", "jkl");
        boolean b = strings.stream().anyMatch(s -> s == "abc");
        System.out.println(b);
        System.out.println("========================================");
        boolean e = strings.stream().anyMatch(str -> {
            System.out.println(1);
            return str.contains("e");
        });
        System.out.println(e);

    }

    /**
     * 检查是否匹配所有元素，返回boolean。
     */
    @Test
    void allMatch() {
        List<String> strings = Arrays.asList("abc", "abd", "aba", "efg", "abcd","jkl", "jkl");
        boolean b = strings.stream().allMatch(s -> s == "abc");
    }

    /**
     *检查是否没有匹配所有元素，返回boolean。
     */
    @Test
    void noneMatch() {
        List<String> strings = Arrays.asList("abc", "abd", "aba", "efg", "abcd","jkl", "jkl");
        boolean b = strings.stream().noneMatch(s -> s == "abc");
    }

    /**
     * 将返回当前流中的任意元素。
     */
    @Test
    void findAny() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        for (int i = 0; i < 20; i++) {
            Optional<String> any = strings.stream().findAny(); //只拿第一个元素
            System.out.println(any);
            Optional<String> any01 = strings.parallelStream().findAny(); //并行的  拿任意一个元素
            System.out.println(any01);
        }


    }

    /**
     * 返回第一个元素
     */
    @Test
    void findFirst() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        Optional<String> first = strings.stream().findFirst();
    }

    /**
     * 遍历流
     */
    @Test
    void forEach() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        strings.stream().forEach(s -> System.out.println(s));
        strings.stream().forEach(System.out::println); //简写，效果一样

    }

    /**
     * 收集器，将流转换为其他形式。
     */
    @Test
    void collect() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        Set<String> set = strings.stream().collect(Collectors.toSet()); //去重复
        System.out.println("set"+set);
        List<String> list = strings.stream().collect(Collectors.toList());
        System.out.println("list"+list);
        Map<String, String> map = strings.stream().collect(Collectors.toMap(k ->k.concat("_name"), v -> v, (v, v1) -> v1));
        System.out.println("map"+map);
    }


    /**
     * 可以将流中元素反复结合起来，得到一个值
     */
    @Test
    void reduce() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        Optional<String> reduce = strings.stream().reduce((acc,item) -> {return acc+item;});
        if(reduce.isPresent()) System.out.println(reduce.get());
    }


    /**
     * 返回流中元素总数。
     */
    @Test
    void count() {
        List<String> strings = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");
        long count = strings.stream().count();
    }

}
