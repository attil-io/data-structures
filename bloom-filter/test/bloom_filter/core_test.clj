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

