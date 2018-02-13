(ns linked-list.core)

(defn create-linked-list [] {})

(defn add-to-linked-list [{:keys [value next-node] :as linked-list} new-value] 
      (let [new-node {:value new-value :next-node nil}]
      (if (= {} linked-list)
          new-node
          (if (nil? next-node) 
              (assoc-in linked-list [:next-node] new-node)
              (assoc-in linked-list [:next-node] (add-to-linked-list next-node new-value))))))

