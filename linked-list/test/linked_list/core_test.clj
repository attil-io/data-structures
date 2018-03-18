(ns linked-list.core-test
  (:require [clojure.test :refer :all]
            [linked-list.core :refer :all]))

(deftest create-linked-list-test
  (testing "create linked list"
    (is (= nil (create-linked-list)))))

(defn new-node 
  ([value] (new-node value nil))
  ([value next-node] (->Node value next-node)))

(defn new-linked-list [& values] 
  (loop [value (last values)
         other-values (reverse values)
         node nil]
  (if (empty? other-values) node
      (recur (first (rest other-values)) (rest other-values) (new-node value node)))))

(def empty-linked-list (new-linked-list))
(def one-element-linked-list (new-linked-list 10))
(def two-element-linked-list (new-linked-list 10 20))
(def three-element-linked-list (new-linked-list 10 20 30))


(deftest add-to-linked-list-test
  (testing "add to linked list"
    (is (= one-element-linked-list (add-to-linked-list empty-linked-list 10)))
    (is (= two-element-linked-list (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20)))
    (is (= three-element-linked-list (add-to-linked-list (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20) 30)))))

(deftest contains-linked-list-test
    (testing "does a linked list contain a value"
      (is (false? (contains-linked-list? empty-linked-list 10)))
      (is (true?  (contains-linked-list? one-element-linked-list 10)))
      (is (true?  (contains-linked-list? two-element-linked-list 20)))
      (is (false?  (contains-linked-list? two-element-linked-list 30)))))

(deftest get-nth-linked-list-test
  (testing "get the nth element of linked list"
    (is (nil? (get-nth-linked-list empty-linked-list 0)))
    (is (= 10 (get-nth-linked-list one-element-linked-list 0)))
    (is (= 20 (get-nth-linked-list two-element-linked-list 1)))))


(deftest without-element-linked-list-test
  (testing "remove the nth element of linked list"
    (is (= empty-linked-list (without-element-linked-list empty-linked-list 0)))
    (is (= empty-linked-list (without-element-linked-list one-element-linked-list 0)))
    (is (= (new-linked-list 20) (without-element-linked-list two-element-linked-list 0)))
    (is (= one-element-linked-list (without-element-linked-list two-element-linked-list 1)))
    (is (= (new-linked-list 10 30) (without-element-linked-list three-element-linked-list 1)))))

 
