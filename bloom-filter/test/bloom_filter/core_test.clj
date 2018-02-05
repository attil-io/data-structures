(ns bloom-filter.core-test
  (:require [clojure.test :refer :all]
            [bloom-filter.core :refer :all]))

(deftest create-test
  (testing "create bloom filter"
    (is (= nil (bloom-create nil nil)))
    (is (= nil (bloom-create 0 nil)))
    (is (= nil (bloom-create nil [])))
))

