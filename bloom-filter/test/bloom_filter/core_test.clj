(ns bloom-filter.core-test

  (:require [clojure.test :refer :all]
            [bloom-filter.core :refer :all]))

(deftest create-test
  (letfn [(always-zero [dontcare] 0)]
  (testing "create bloom filter"
    (is (= nil (bloom-create nil nil)))
    (is (= nil (bloom-create 0 nil)))
    (is (= nil (bloom-create nil [])))
    (is (= {:bits [] :hash-functions []} (bloom-create 0 [])))
    (is (= {:bits [0] :hash-functions []} (bloom-create 1 [])))
    (is (= {:bits [] :hash-functions [always-zero]} (bloom-create 0 [always-zero])))
)))

(deftest add-test
  (let [mod7fun (fn [num] (mod num 7))
        empty-filter (bloom-create 7 [mod7fun])]
  (testing "add to bloom filter"
    (is (= nil (bloom-add nil 3)))
    (is (= nil (bloom-add empty-filter nil)))
    (is (= {:bits [0 0 0 1 0 0 0] :hash-functions [mod7fun]}
           (bloom-add empty-filter 3)))
)))

