(ns linked-list.core-test
  (:require [clojure.test :refer :all]
            [linked-list.core :refer :all]))

(deftest create-linked-list-test
  (testing "create linked list"
    (is (= {} (create-linked-list)))))

(def empty-linked-list (create-linked-list))

(deftest add-to-linked-list-test
  (testing "add to linked list"
    (is (= {:value 10 :next-node nil} (add-to-linked-list empty-linked-list 10)))
    (is (= {:value 10 :next-node {:value 20 :next-node nil}} (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20)))
    (is (= {:value 10 :next-node {:value 20 :next-node {:value 30 :next-node nil}}} (add-to-linked-list (add-to-linked-list (add-to-linked-list empty-linked-list 10) 20) 30)))))

(deftest contains-linked-list-test
  (let [one-element-linked-list (add-to-linked-list empty-linked-list 10)
        two-element-linked-list (add-to-linked-list one-element-linked-list 20)]
  (testing "does a linked list contain a value"
    (is (false? (linked-list-contains? empty-linked-list 10)))
    (is (true?  (linked-list-contains? one-element-linked-list 10)))
    (is (true?  (linked-list-contains? two-element-linked-list 20)))
    (is (false?  (linked-list-contains? two-element-linked-list 30))))))

