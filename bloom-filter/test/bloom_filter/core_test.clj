(ns bloom-filter.core-test

(:require [clojure.test :refer :all]
          [bloom-filter.core :refer :all]))

(def ^:const F false)
(def ^:const T true)

(defn mod7-fun [num] (mod num 7))
(defn always-zero-fun [dontcare] 0)  
(defn always-ten-fun [dontcare] 10)  

(def empty-filter (bloom-create 7 []))
(def single-fun-filter (bloom-create 7 [mod7-fun]))
(def too-high-range-fun-filter (bloom-create 7 [always-ten-fun]))

(deftest create-test
  (testing "create bloom filter"
    (is (thrown-with-msg? AssertionError #"numbits must be numeric" (bloom-create nil nil)))
    (is (thrown-with-msg? AssertionError #"numbits must be numeric" (bloom-create "a" nil)))
    (is (thrown-with-msg? AssertionError #"hash-functions must not be nil" (bloom-create 0 nil)))
    (is (thrown-with-msg? AssertionError #"hash-functions must be a collection of functions" (bloom-create 0 "a")))
    (is (thrown-with-msg? AssertionError #"hash-functions must be a collection of functions" (bloom-create 0 ["a"])))
    (is (thrown-with-msg? AssertionError #"numbits must be numeric" (bloom-create nil [])))
    (is (= {:bits [] :hash-functions []} (bloom-create 0 [])))
    (is (= {:bits [F] :hash-functions []} (bloom-create 1 [])))
    (is (= {:bits [] :hash-functions [always-zero-fun]} (bloom-create 0 [always-zero-fun])))
))

(deftest add-test
  (let [two-fun-filter (bloom-create 7 [mod7-fun always-zero-fun])]
  (testing "add to bloom filter"
    (is (= nil (bloom-add nil 3)))
    (is (= empty-filter (bloom-add empty-filter nil)))
    (is (= empty-filter (bloom-add empty-filter 10)))
    (is (= [F F F T F F F] (:bits (bloom-add single-fun-filter 3))))
    (is (= [T F F T F F F] (:bits (bloom-add two-fun-filter 3))))
    (is (= [F F F T F F F] (:bits (bloom-add too-high-range-fun-filter 3))))
)))

(deftest contains-test
  (let [filter-with-element (bloom-add single-fun-filter 3)]
  (testing "bloom filter contains"
    (is (true? (bloom-contains empty-filter 0)))
    (is (false? (bloom-contains single-fun-filter 0)))
    (is (true? (bloom-contains filter-with-element 3)))
    (is (true? (bloom-contains filter-with-element 10)))
)))
 
