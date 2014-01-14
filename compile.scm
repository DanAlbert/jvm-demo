(use-modules (ice-9 format))

(define (compile prog)
  (let* ((symbol (if (list? prog) (car prog) prog)))
	(cond ((eq? symbol 'if) (compile-if prog))
		  ((symbol? symbol) (compile-expr prog))
		  ((number? symbol) (gen-number prog))
		  (else (error 'compile (format #t "unknown symbol ~a" symbol))))))

(define (compile-expr expr)
  (let* ((func (car expr))
		 (left (cadr expr))
		 (right (caddr expr)))
	(begin
	  (compile left)
	  (compile right)
	  (display (format "~a " func)))))

(define (gen-number num)
  (display (format #f "ldc ~a " num))
  (newline))

(define (gen-class-header class-name)
  (begin
	(display (string-append ".class public " class-name))(newline)
	(display ".super java/lang/Object")(newline)))

(define (gen-method name args type body)
  (display "Unimplemented")(newline))

(gen-class-header "Main")
;(compile '(+ (- 5 2) (* 3 4)))
;(newline)
