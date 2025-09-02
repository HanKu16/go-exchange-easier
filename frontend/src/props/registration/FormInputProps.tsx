export interface FormInputProps {
  label: string;
  id: string;
  value: string;
  isObligatory: boolean;
  suggestions: string[];
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}