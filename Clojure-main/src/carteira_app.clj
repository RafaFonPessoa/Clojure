(ns carteira-app
  (:require [api :as api]
            [ui :as ui]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; RODANDO A APLICAÇÃO ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn menu-principal []
  (loop []
    (println "----------------------------------------------------")
    (println "Escreva o número para selecionar a opção desejada:")
    (println "1) Ver Lista Completa de Companhias cadastradas na B3")
    (println "2) Exibir dados da ação de uma companhia")
    (println "3) Registrar transação de compra")
    (println "4) Registrar transação de venda")
    (println "5) Exibir extrato completo")
    (println "6) Exibir extrato por tipo (compra/venda)")
    (println "7) Exibir saldo total")
    (println "8) Sair")
    (println "----------------------------------------------------")
    (println "Sua Escolha: ")

    (let [selecao (Integer. (read-line))]
      (cond
        (= selecao 1) (do
                        (api/exibir-lista-companhias)
                        (recur))
        (= selecao 2) (do
                        (println "Digite o nome da companhia:")
                        (api/exibir-dados-acao (read-line))
                        (recur))
        (= selecao 3) (do
                        (println "Digite o código da ação e depois a quantidade:")
                        (let [codigo (read-line)
                              quantidade (Integer. (read-line))
                              cotacao (api/obter-cotacao codigo)
                              valor (if cotacao (:regularMarketPrice (first (:results cotacao))))]
                          (api/registrar-compra codigo quantidade valor)
                          (recur)))
        (= selecao 4) (do
                        (println "Digite o código da ação e depois a quantidade:")
                        (let [codigo (read-line)
                              quantidade (Integer. (read-line))
                              ]
                          (api/registrar-venda codigo quantidade)
                          (recur)))

        (= selecao 5) (do
                        (api/exibir-extrato-completo)
                        (recur))
        (= selecao 6) (do
                        (println "Digite o tipo (compra/venda):")
                        (let [tipo (read-line)]
                          (println (str "Exibindo transações do tipo: " tipo))
                          (api/exibir-transacoes-tipo tipo)
                          (recur)))
        (= selecao 7) (do
                        (api/exibir-saldo-total)
                        (recur))
        (= selecao 8) (do
                        (println "Saindo...")
                        (ui/fim))
        :else (do
                (println "Opção inválida. Tente novamente.")
                (recur))))))

(ui/banner)
(ui/menu)
(println "")
(menu-principal)




