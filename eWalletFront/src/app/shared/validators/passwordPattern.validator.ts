import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function passwordPatternValidator(): ValidatorFn {
    
    return (ctrl: AbstractControl): null | ValidationErrors => {
        const regex: RegExp = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,}$/;
        if(regex.test(ctrl.value) || ctrl.value === '') {
            return null;
        } else {
            return {
                passwordPatternValidator: ctrl.value
            };
        }
    };
}