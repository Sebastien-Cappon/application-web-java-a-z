import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function emailPatternValidator(): ValidatorFn {
    
    return (ctrl: AbstractControl): null | ValidationErrors => {
        const regex = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$');
        if(regex.test(ctrl.value) || ctrl.value === '') {
            return null;
        } else {
            return {
                emailPatternValidator: ctrl.value
            };
        }
    };
}