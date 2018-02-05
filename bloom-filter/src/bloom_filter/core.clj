(ns bloom-filter.core)


(defn bloom-create [numbits hash-functions]
      (if (or (nil? numbits) (nil? hash-functions)) nil
          {:bits (repeat numbits 0) :hash-functions hash-functions}))
