import type { FormInputProps } from "../../props/registration/FormInputProps"

const FormInput = ( {label, id, value, isObligatory, 
  suggestions, onChange }: FormInputProps ) => {

  return (
      <div className="w-7/10 flex justify-center items-center">
        <div className="flex flex-col w-9/10 !pl-10">
          <label htmlFor={id} className="text-[1vw]">
            { label }
            <span className="text-yellow-500 font-bold text-[1.3vw]">
              { isObligatory ? "*" : "" }
            </span>
          </label>
          <input
            id={id}
            type="text"
            value={value}
            onChange={onChange}
            placeholder={`Enter your ${label.toLowerCase()}`}
            className="outline outline-neutral-300 focus:outline-neutral-400 
              focus:outline-2 rounded-lg shadow-md !px-1.5 !py-1 w-17/20 !my-2"
          />
          {suggestions.map(s => (
            <p key={s} className="text-zinc-500 text-[0.8vw]">{`-${ s }`}</p>
          ))}
        </div>
      </div>
  )
}

export default FormInput;