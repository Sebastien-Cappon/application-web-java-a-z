import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function confirmEqualsValidator(main: string, confirm: string): ValidatorFn {
    
    return (ctrl: AbstractControl): null | ValidationErrors => {
        const mainValue = ctrl.get(main)!.value;
        const confirmValue = ctrl.get(confirm)!.value;
        
        return mainValue === confirmValue ? null : {
            confirmEquals : {
                main: mainValue,
                confirm: confirmValue
            }
        };
    };
}