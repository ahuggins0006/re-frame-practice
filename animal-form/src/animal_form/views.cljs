(ns animal-form.views
  (:require
   [re-frame.core :as re-frame]
   [animal-form.events :as events]
   [animal-form.subs :as subs]
   ))

(def animal-types ["dog" "cat" "bird"])

(defn text-input [id]
  "Taken from https://bulma.io/documentation/form/general/"
  (let [value (re-frame/subscribe [::subs/form id])]
  [:div.field
   [:label.label "Name"]
   [:div.control
    [:input.input {:value @value
                   :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])
                   :type "text" :placeholder "Text input"}]]]))

(defn select-input [id label options]
  "Taken from https://bulma.io/documentation/form/general/"
  (let [value (re-frame/subscribe [::subs/form id])]
  [:div.field
   [:label.label label]
   [:div.control
    [:div.select
     [:select {:value @value :on-change #(re-frame/dispatch [::events/update-form id (-> % .-target .-value)])}
      [:option {:value ""} "Please select"]
     (map (fn [o] [:option {:key o :value o} o]) options)
      ]]]]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.section
     [text-input :animal-name "Animal Name"]
     [select-input :animal-type "Animal Name" animal-types]
     [:button.button.is-primary {:on-click #(re-frame/dispatch [::events/save-form])} "Save"]]))
