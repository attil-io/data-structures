(ns linked-list.core)

(defn create-linked-list [] {})

(defn add-to-linked-list [{:keys [value next-node] :as linked-list} new-value] 
      (let [new-node {:value new-value :next-node nil}]
      (if (= {} linked-list)
          new-node
          (if (nil? next-node)
              (assoc-in linked-list [:next-node] new-node)
              (assoc-in linked-list [:next-node] (recur next-node new-value))))))

(defn contains-linked-list? [{:keys [value next-node] :as linked-list} query-value]
      (if (empty? linked-list)
          false
          (or (= value query-value) (recur next-node query-value))))

(defn get-nth-linked-list [{:keys [value next-node] :as linked-list} n]
      (if (< n 1)
          value
          (recur next-node (dec n))))

