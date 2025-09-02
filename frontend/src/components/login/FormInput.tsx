import type { FormInputProps } from "../../props/FormInputProps"

const FormInput = ({ label, id, value, onChange, placeholder }: FormInputProps) => {
  return (
    <div className="flex items-center justify-center">
      <div className="w-full flex flex-col">
        <label htmlFor={id} className="!mb-2 text-[1vw]">
          {label}
        </label>
        <input
          id={id}
          type="text"
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          className="outline outline-neutral-300 focus:outline-neutral-400 
            focus:outline-2 rounded-lg shadow-md !px-1.5 !py-1"
        />
      </div>
    </div>
  );
};

export default FormInput;