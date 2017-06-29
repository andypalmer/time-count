(ns time-count.allens-interval-algebra-tests
  (:require [time-count.allens-interval-algebra :refer :all]
            [midje.sweet :refer :all]
            [time-count.meta-joda :refer [destringify]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;  Allen's Interval Algebra  ;;;
;;;  Basic relations           ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(facts "about Allen's Interval Algebra"
       (fact "Thirteen basic relations in Allen's Interval Algebra"
             (relation-str "2017" "2017") => :equal
             (relation-str "2015" "2017") => :before
             (relation-str "2017" "2015") => :after
             (relation-str "2016" "2017") => :meets
             (relation-str "2017" "2016") => :met-by
             (relation-str "2017-01" "2017") => :starts
             (relation-str "2017" "2017-01") => :started-by
             (relation-str "2017-12" "2017") => :finishes
             (relation-str "2017" "2017-12") => :finished-by
             (relation-str "2017-02" "2017") => :during
             (relation-str "2017" "2017-02") => :contains
             (relation-str "2017-W05" "2017-02") => :overlaps
             (relation-str "2017-02" "2017-W05") => :overlapped-by)

       (fact "If scales are different."
             (relation-str "2016-12" "2017") => :meets
             (relation-str "2016-11" "2017") => :before
             (relation-str "2017-W01" "2017") => :during))


(facts "about Allen's Interval Algebra"
       (fact "Thirteen basic relations in Allen's Interval Algebra"
             (relation-gen-str "2017" "2017") => :equal
             (relation-gen-str "2015" "2017") => :before
             (relation-gen-str "2017" "2015") => :after
             (relation-gen-str "2016" "2017") => :meets
             (relation-gen-str "2017" "2016") => :met-by
             (relation-gen-str "2017-01" "2017") => :starts
             (relation-gen-str "2017" "2017-01") => :started-by
             (relation-gen-str "2017-12" "2017") => :finishes
             (relation-gen-str "2017" "2017-12") => :finished-by
             (relation-gen-str "2017-02" "2017") => :during
             (relation-gen-str "2017" "2017-02") => :contains
             (relation-gen-str "2017-W05" "2017-02") => :overlaps
             (relation-gen-str "2017-02" "2017-W05") => :overlapped-by)

       (fact "If scales are different."
             (relation-gen-str "2016-12" "2017") => :meets
             (relation-gen-str "2016-11" "2017") => :before
             (relation-gen-str "2017-W01" "2017") => :during))

(fact
  (relation-gen {:starts (destringify "2017") :ends (destringify "2018")}
                {:starts (destringify "2020") :ends (destringify "2021")}) => :before
  (relation-gen {:starts (destringify "2017") :ends (destringify "2020")}
                {:starts (destringify "2019") :ends (destringify "2021")}) => :overlaps
  (relation-gen (destringify "2017")
                {:starts (destringify "2020") :ends (destringify "2021")}) => :before)


(fact "example: invoice due date"
      (letfn [(overdue? [due-date now] (#{:after :met-by} (relation-str now due-date)))]
        (overdue? "2017-02-15" "2017-02-10T14:30") => falsey
        (overdue? "2017-02-15" "2017-02-15T20:30") => falsey
        (overdue? "2017-02-15" "2017-02-15T23:59") => falsey
        (overdue? "2017-02-15" "2017-02-16T00:00") => truthy
        (overdue? "2017-02-15" "2017-02-17T10:04") => truthy))


(fact "These functions should be private, but they need testing :-("
      (starts-to-dt-left (destringify "2017-02")) => (-> "2017-02" destringify first)
      (starts-to-dt-left {:starts (destringify "2017-02")}) => (-> "2017-02" destringify first)
      )