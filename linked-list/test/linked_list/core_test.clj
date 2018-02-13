(ns linked-list.core-test
  (:require [clojure.test :refer :all]
            [linked-list.core :refer :all]))

(deftest create-linked-list-test
  (testing "create linked list"
    (is (= {} (create-linked-list)))))
