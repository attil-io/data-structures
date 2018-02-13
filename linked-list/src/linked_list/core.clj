(ns linked-list.core)

(defn create-linked-list [] {})

(defn add-to-linked-list [{:keys [value next-node] :as linked-list} new-value] 
      (let [new-node {:value new-value :next-node nil}]
      (if (= {} linked-list)
          new-node
          (let [new-tail (if (nil? next-node) new-node (add-to-linked-list next-node new-value))]
              (assoc-in linked-list [:next-node] new-tail)))))

(defn linked-list-contains? [linked-list value]
      false)
