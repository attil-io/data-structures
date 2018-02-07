(ns bloom-filter.core-test

(:require [clojure.test :refer :all]
          [bloom-filter.core :refer :all]))

(defn mod7-fun [num] (mod num 7))
(defn always-zero-fun [dontcare] 0)  

(deftest create-test
  (testing "create bloom filter"
    (is (= nil (bloom-create nil nil)))
    (is (= nil (bloom-create 0 nil)))
    (is (= nil (bloom-create nil [])))
    (is (= {:bits [] :hash-functions []} (bloom-create 0 [])))
    (is (= {:bits [0] :hash-functions []} (bloom-create 1 [])))
    (is (= {:bits [] :hash-functions [always-zero-fun]} (bloom-create 0 [always-zero-fun])))
))

(deftest add-test
  (let [empty-filter (bloom-create 7 [])
        single-fun-filter (bloom-create 7 [mod7-fun])
        two-fun-filter (bloom-create 7 [mod7-fun always-zero-fun])]
  (testing "add to bloom filter"
    (is (= nil (bloom-add nil 3)))
    (is (= empty-filter (bloom-add empty-filter nil)))
    (is (= empty-filter (bloom-add empty-filter 10)))
    (is (= {:bits [0 0 0 1 0 0 0] :hash-functions [mod7-fun]}
           (bloom-add single-fun-filter 3)))
    (is (= {:bits [1 0 0 1 0 0 0] :hash-functions [mod7-fun always-zero-fun]}
           (bloom-add two-fun-filter 3)))
)))

(deftest contains-test
  (let [empty-filter (bloom-create 7 [])
        simple-filter (bloom-create 7 [mod7-fun])
        filter-with-element (bloom-add simple-filter 3)]
  (testing "bloom filter contains"
    (is (true? (bloom-contains empty-filter 0)))
    (is (false? (bloom-contains simple-filter 0)))
    (is (true? (bloom-contains filter-with-element 3)))
    (is (true? (bloom-contains filter-with-element 10)))
)))
 
