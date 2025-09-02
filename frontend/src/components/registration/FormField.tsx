import type { FormFieldProps } from "../../props/registration/FormFieldProps"
import FormInput from "./FormInput"
import FormInputDescription from "./FormInputDescription"

const FormField = ({ inputProps, descriptionProps } : FormFieldProps) => {
  return (
      <div className="h-1/10 w-full flex">
        <FormInput label={ inputProps.label } id={ inputProps.id } 
          value={ inputProps.value } isObligatory={ inputProps.isObligatory } 
          suggestions={ inputProps.suggestions }
          onChange={ inputProps.onChange }/>  
        <FormInputDescription text={ descriptionProps.text }/>
      </div>
  )
}

export default FormField