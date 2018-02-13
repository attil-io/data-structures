(ns linked-list.core)

(defn create-linked-list [] {})

(defn add-to-linked-list [{:keys [value next] :as linked-list} new-value] 
      (let [new-node {:value new-value :next nil}]
      (if (= {} linked-list)
          new-node
          (assoc-in linked-list [:next] new-node))))

