(ns linked-list.core-test
  (:require [clojure.test :refer :all]
            [linked-list.core :refer :all]))

(deftest create-linked-list-test
  (testing "create linked list"
    (is (= nil (create-linked-list)))))

(defn new-node 
  ([value] (new-node value nil))
  ([value next-node] (->Node value next-node)))

(def empty-linked-list (create-linked-list))

(deftest add-to-linked-list-test
  (testing "add to linked list"
    (is (= (new-node 10) (add-to-linked-list empty-linked-list 10)))
    (is (= (new-node 10 (new-node 20)) (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20)))
    (is (= (new-node 10 (new-node 20 (new-node 30))) (add-to-linked-list (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20) 30)))))

(def one-element-linked-list (add-to-linked-list empty-linked-list 10))
(def two-element-linked-list (add-to-linked-list one-element-linked-list 20))

(deftest contains-linked-list-test
  (let [one-element-linked-list (add-to-linked-list empty-linked-list 10)
        two-element-linked-list (add-to-linked-list one-element-linked-list 20)]
    (testing "does a linked list contain a value"
      (is (false? (contains-linked-list? empty-linked-list 10)))
      (is (true?  (contains-linked-list? one-element-linked-list 10)))
      (is (true?  (contains-linked-list? two-element-linked-list 20)))
      (is (false?  (contains-linked-list? two-element-linked-list 30))))))

(deftest get-nth-linked-list-test
  (testing "get the nth element of linked list"
    (is (nil? (get-nth-linked-list empty-linked-list 0)))
    (is (= 10 (get-nth-linked-list one-element-linked-list 0)))
    (is (= 20 (get-nth-linked-list two-element-linked-list 1)))))

(def three-element-linked-list (add-to-linked-list two-element-linked-list 30))

(deftest without-element-linked-list-test
  (testing "remove the nth element of linked list"
    (is (= empty-linked-list (without-element-linked-list empty-linked-list 0)))
    (is (= empty-linked-list (without-element-linked-list one-element-linked-list 0)))
    (is (= (new-node 20) (without-element-linked-list two-element-linked-list 0)))
    (is (= one-element-linked-list (without-element-linked-list two-element-linked-list 1)))
    (is (= (new-node 10 (new-node 30)) (without-element-linked-list three-element-linked-list 1)))))

 
