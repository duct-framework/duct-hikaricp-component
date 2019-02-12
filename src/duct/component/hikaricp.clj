(ns duct.component.hikaricp
  (:require [com.stuartsierra.component :as component])
  (:import [com.zaxxer.hikari HikariConfig HikariDataSource]))

(defn- make-config
  [{:keys [uri driver-class-name username password auto-commit? conn-timeout
           idle-timeout max-lifetime conn-test-query min-idle max-pool-size pool-name
           register-mbeans? metric-registry]}]
  (let [cfg (HikariConfig.)]
    (when uri                  (.setJdbcUrl cfg uri))
    (when driver-class-name    (.setDriverClassName cfg driver-class-name))
    (when username             (.setUsername cfg username))
    (when password             (.setPassword cfg password))
    (when (some? auto-commit?) (.setAutoCommit cfg auto-commit?))
    (when conn-timeout         (.setConnectionTimeout cfg conn-timeout))
    (when idle-timeout         (.setIdleTimeout cfg idle-timeout))
    (when max-lifetime         (.setMaxLifetime cfg max-lifetime))
    (when max-pool-size        (.setMaximumPoolSize cfg max-pool-size))
    (when metric-registry      (.setMetricRegistry cfg metric-registry))
    (when min-idle             (.setMinimumIdle cfg min-idle))
    (when pool-name            (.setPoolName cfg pool-name))
    (when register-mbeans?     (.setRegisterMbeans cfg register-mbeans?))
    cfg))

(defn- make-spec [component]
  {:datasource (HikariDataSource. (make-config component))})

(defrecord HikariCP [uri]
  component/Lifecycle
  (start [component]
    (if (:spec component)
      component
      (assoc component :spec (make-spec component))))
  (stop [component]
    (if-let [spec (:spec component)]
      (do (.close (:datasource spec))
          (dissoc component :spec))
      component)))

(defn hikaricp [options]
  {:pre [(:uri options)]}
  (map->HikariCP options))
