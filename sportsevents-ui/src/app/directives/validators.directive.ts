import { Directive } from "@angular/core";
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from "@angular/forms";

  
  @Directive({
    selector: "[isPastDate]",
    exportAs: "isPastDate",
    providers: [{
      provide: NG_VALIDATORS, useExisting: IsPastDateDirective, multi: true
    }]
  })
  export class IsPastDateDirective implements Validator {
    validate(control: AbstractControl): ValidationErrors | null{
      const value = control.value;
      if(!value){
        return null;
      }

      const date = new Date(value);
      const now = Date.now();

      return now > date.getTime() ? {isPast: true} : null;
    }
  }
  