import type { FormInputDescriptionProps } from "../../props/registration/FormInputDescriptionProps"

const FormInputDescription = ({ text }:  FormInputDescriptionProps ) => {
  return (
    <div className="flex justify-center items-center text-gray-400 w-3/10 text-center">
      <p className="w-7/10 !pr-6">
        { text }
      </p>
    </div>
  )
}

export default FormInputDescription